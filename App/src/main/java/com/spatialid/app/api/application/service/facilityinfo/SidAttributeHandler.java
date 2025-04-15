// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.facilityinfo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spatialid.app.api.constants.FacilityInfoConstants;
import com.spatialid.app.api.presentation.dto.facilityinfo.AttributeDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.FacilitySidDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetSidAttributeRequestDto;
import com.spatialid.app.common.exception.subexception.DataRangeException;
import com.spatialid.app.common.exception.subexception.DataUpdateFailureException;
import com.spatialid.app.common.exception.subexception.InternalApiCallingException;
import com.spatialid.app.common.resource.ErrorResponse;

/**
 * 空間・属性情報参照を行うハンドラクラス．<br>
 * 埋設物情報取得における終端ハンドラ．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Service
public class SidAttributeHandler implements IBaseHandler<GetSidAttributeRequestDto, AttributeDto> {
    
    /**
     * HTTPクライアント．
     */
    private final RestClient restClient;
    
    /**
     * Jsonオブジェクトマッパー．
     */
    private final ObjectMapper objectMapper;
    
    public SidAttributeHandler(@Qualifier("commonApiClient") RestClient restclient,
            ObjectMapper objectMapper) {
        
        this.restClient = restclient;
        this.objectMapper = objectMapper;
        
    }
    
    /**
     * 次に実行するハンドラを設定する．<br>
     * 終端ハンドラのため、例外を送出するのみ．
     * 
     * @param <V> 次に実行するハンドラが受け取る引数の型
     * @param nextHandler 次に実行するハンドラ
     */
    @Override
    public <V> IBaseHandler<V, AttributeDto> setNext(IBaseHandler<V, AttributeDto> nextHandler) throws Exception {
        
        throw new Exception();
        
    }
    
    /**
     * 空間・属性情報参照の呼び出しを行う．
     * 
     * @param getSidAttributeRequestDto 空間・属性情報参照へのリクエストDTO
     * @return 埋設物の情報とその空間IDを保持するオブジェクト
     */
    @Override
    public AttributeDto handle(GetSidAttributeRequestDto getSidAttributeRequestDto) throws Exception {
                
        final String path = FacilityInfoConstants.SID_ATTRIBUTE_URI;
        
        final ResponseEntity<String> rawResponse = restClient.method(HttpMethod.POST)
                .uri(path)
                .accept(MediaType.APPLICATION_JSON)
                .body(getSidAttributeRequestDto)
                .retrieve()
                .toEntity(String.class);
                
        List<FacilitySidDto> facilitySidDtoList = new ArrayList<>();

        if (rawResponse.getStatusCode().isError()) {
            
            ErrorResponse errorResponse = objectMapper.readValue(rawResponse.getBody(), ErrorResponse.class);
            
            String errMsg = errorResponse.getMessage();
            
            if (errMsg.matches(FacilityInfoConstants.REGEX_DATA_LOCKED)) {
                
                throw new DataUpdateFailureException();
                
            } else if(errMsg.matches(FacilityInfoConstants.REGEX_INVALID_DATA_RANGE)) {
                
                throw new DataRangeException();
                
            }
            
            throw new InternalApiCallingException(FacilityInfoConstants.SID_ATTRIBUTE_NAME);
            
        } else {
            
            facilitySidDtoList = objectMapper.readValue(objectMapper.readTree(rawResponse.getBody()).path("sidAttributeList").toString(), new TypeReference<List<FacilitySidDto>>() {});
            
        }
        
        final boolean isFacilityExist = (0 < facilitySidDtoList.size());
        
        final AttributeDto attributeDto = AttributeDto.builder()
                .isFacilityExist(isFacilityExist)
                .facilitySidList(facilitySidDtoList)
                .build();
        
        return attributeDto;
        
    }
      
}