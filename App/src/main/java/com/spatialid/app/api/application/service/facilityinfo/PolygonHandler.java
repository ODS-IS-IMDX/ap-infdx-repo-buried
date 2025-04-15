// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.facilityinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.spatialid.app.api.constants.FacilityInfoConstants;
import com.spatialid.app.api.presentation.dto.facilityinfo.AttributeDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.FacilityInfoChainDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetPolygonAreaRequestDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetPolygonAreaResponseDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.PolygonChainDto;
import com.spatialid.app.common.exception.subexception.InternalApiCallingException;
import com.spatialid.app.common.exception.subexception.ParamErrorException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

/**
 * ポリゴン面積取得を行うハンドラクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Service
public class PolygonHandler implements IBaseHandler<FacilityInfoChainDto, AttributeDto> {
    
    /**
     * 次に実行するハンドラ．
     */
    private IBaseHandler<PolygonChainDto, AttributeDto> nextHandler;
    
    /**
     * HTTPクライアント．
     */
    private final RestClient restClient;
    
    /**
     * Jsonオブジェクトマッパー．
     */
    private final ObjectMapper objectMapper;
    
    /**
     * バリデータ．
     */
    private final Validator validator;
    
    public PolygonHandler(@Qualifier("spatialIdApiClient") RestClient restclient,
            ObjectMapper objectMapper,
            Validator validator) {
        
        this.restClient = restclient;
        this.objectMapper = objectMapper;
        this.validator = validator;
        
    }
    
    /**
     * 次に実行するハンドラを設定する．<br>
     * 型のキャスト部分で型安全が保証しきれないため、実装者が注意すること．
     * 
     * @param <V> 次に実行するハンドラが受け取る引数の型
     * @param nextHandler 次に実行するハンドラ
     * @return 次に実行するハンドラクラス
     */
    @SuppressWarnings("unchecked")
    @Override
    public <V> IBaseHandler<V, AttributeDto> setNext(IBaseHandler<V, AttributeDto> nextHandler) {
        
        this.nextHandler = (IBaseHandler<PolygonChainDto, AttributeDto>) nextHandler;
        
        return nextHandler;
        
    }
    
    /**
     * ポリゴン面積取得の呼び出しを行う．
     * 
     * @param chainDto 埋設物情報取得へのリクエストに利用者システムIDを追加したDTO
     * @return 終端ハンドラからの返り値
     */
    @Override
    public AttributeDto handle(FacilityInfoChainDto chainDto) throws Exception {
        
        final GetPolygonAreaRequestDto getPolygonAreaDto = GetPolygonAreaRequestDto.builder()
                .polygon(formatPolygon(chainDto.getSearchArea()))
                .epsgCode(chainDto.getEpsgCode())
                .build();
        
        final ResponseEntity<String> rawResponse = restClient.method(HttpMethod.POST)
                .uri(FacilityInfoConstants.POLYGON_AREA_URI)
                .accept(MediaType.APPLICATION_JSON)
                .body(getPolygonAreaDto)
                .retrieve()
                .toEntity(String.class);
        
        GetPolygonAreaResponseDto getPolygonAreaResponseDto = null;
        
        if (rawResponse.getStatusCode().isError()) {
            
            if (rawResponse.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)) {
                
                Map<String, String> violationFields = new HashMap<String, String>();
                
                violationFields.put("searchArea", chainDto.getSearchArea().toString());
                
                throw new ParamErrorException(violationFields);

            } else {
                
                throw new InternalApiCallingException(FacilityInfoConstants.POLYGON_AREA_NAME);
                
            }
            
            
        } else {
            
            getPolygonAreaResponseDto = objectMapper.readValue(rawResponse.getBody(), GetPolygonAreaResponseDto.class);
            
        }
        
        // バリデーションの手動実行
        Set<ConstraintViolation<Object>> violations = validator.validate(getPolygonAreaResponseDto);
        
        if (!(violations.isEmpty())) {
            
            Map<String, String> violationFields = new HashMap<String, String>();
            
            violationFields.put("searchArea", chainDto.getSearchArea().toString());
            
            throw new ParamErrorException(violationFields);
            
        }
        
        final PolygonChainDto polygonChainDto = PolygonChainDto.builder()
                .polygon(getPolygonAreaDto.getPolygon())
                .searchArea(chainDto.getSearchArea())
                .epsgCode(chainDto.getEpsgCode())
                .servicerId(chainDto.getServicerId())
                .infraCompanyIds(chainDto.getInfraCompanyIds())
                .updateDate(chainDto.getUpdateDate())
                .returnZoomLevel(chainDto.getReturnZoomLevel())
                .build();  
                
        return nextHandler.handle(polygonChainDto);
        
    }
    
    /**
     * 取得対象領域の座標情報をポリゴンの座標情報フォーマットに変換する．
     * 
     * @param searchArea 取得対象領域
     * @return ポリゴンの座標情報
     */
    private String formatPolygon(List<String> searchArea) {
        
        // 緯度・経度のペアをリストのネストで表現
        final List<String> pairedSearchArea = Lists.partition(searchArea, 2)
                .stream()
                .map(pair -> String.join(" ", pair))
                .collect(Collectors.toList());
        
        // 始点と終点を一致させる
        pairedSearchArea.addLast(pairedSearchArea.getFirst());
        
        final StringBuilder polygonBUilder = new StringBuilder();
        
        return polygonBUilder.append("POLYGON((")
                .append(String.join(",", pairedSearchArea))
                .append("))")
                .toString();
        
    }
        
}
