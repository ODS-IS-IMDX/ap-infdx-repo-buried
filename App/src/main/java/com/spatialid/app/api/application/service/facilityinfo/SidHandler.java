// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.facilityinfo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spatialid.app.api.constants.FacilityInfoConstants;
import com.spatialid.app.api.presentation.dto.facilityinfo.AttributeDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetSidAttributeRequestDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetSidRequestDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetSidResponseDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.PolygonChainDto;
import com.spatialid.app.common.exception.subexception.InternalApiCallingException;
import com.spatialid.app.common.exception.subexception.ParamErrorException;

/**
 * 座標→空間ID変換を行うハンドラクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Service
public class SidHandler implements IBaseHandler<PolygonChainDto, AttributeDto> {
    
    /**
     * 次に実行するハンドラ．
     */
    private IBaseHandler<GetSidAttributeRequestDto, AttributeDto> nextHandler;
    
    /**
     * HTTPクライアント．
     */
    private final RestClient restClient;
    
    /**
     * Jsonオブジェクトマッパー．
     */
    private final ObjectMapper objectMapper;
    
    public SidHandler(@Qualifier("spatialIdApiClient") RestClient restclient,
            ObjectMapper objectMapper) {
        
        this.restClient = restclient;
        this.objectMapper = objectMapper;
        
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
        
        this.nextHandler = (IBaseHandler<GetSidAttributeRequestDto, AttributeDto>) nextHandler;
        
        return nextHandler;
        
    }
    
    /**
     * 座標→空間ID変換の呼び出しを行う．
     * 
     * @param chainDto 埋設物情報取得へのリクエストとポリゴン面積取得の過程で得た情報を保持するDTO
     * @return 終端ハンドラからの返り値
     */
    @Override
    public AttributeDto handle(PolygonChainDto chainDto) throws Exception {
        
        final GetSidRequestDto getSidRequestDto = GetSidRequestDto.builder()
                .polygon(chainDto.getPolygon())
                .epsgCode(chainDto.getEpsgCode())
                .zoomLevel(26)
                .build();
        
        final ResponseEntity<String> rawResponse = restClient.method(HttpMethod.POST)
                .uri(FacilityInfoConstants.SID_URI)
                .accept(MediaType.APPLICATION_JSON)
                .body(getSidRequestDto)
                .retrieve()
                .toEntity(String.class);
        
        GetSidResponseDto getSidResponseDto = null;
        
        if (rawResponse.getStatusCode().isError()) {
            
            if (rawResponse.getStatusCode().isSameCodeAs(HttpStatus.BAD_REQUEST)) {
                
                Map<String, String> violationFields = new HashMap<String, String>();
                
                violationFields.put("searchArea", chainDto.getSearchArea().toString());
                
                throw new ParamErrorException(violationFields);

            } else {
                
                throw new InternalApiCallingException(FacilityInfoConstants.SID_NAME);
                
            }
            
            
        } else {
            
            getSidResponseDto = objectMapper.readValue(rawResponse.getBody(), GetSidResponseDto.class);
            
        }
        
        final GetSidAttributeRequestDto getSidAttributeRequestDto = GetSidAttributeRequestDto.builder()
                .sidList(getSidResponseDto.getSidList())
                .isFlatSearch(true)
                .servicerId(chainDto.getServicerId())
                .infraCompanyIdList(chainDto.getInfraCompanyIds())
                .returnZoomLevel(chainDto.getReturnZoomLevel())
                .updateTime(chainDto.getUpdateDate())
                .build();
        
        return nextHandler.handle(getSidAttributeRequestDto);
        
    }
        
}
