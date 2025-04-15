// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.facilityinfo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.spatialid.app.api.presentation.dto.facilityinfo.AttributeDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.FacilityInfoChainDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetSidAttributeRequestDto;

/**
 * 空間IDの平面化を行うハンドラクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Service
public class FlattenHandler implements IBaseHandler<FacilityInfoChainDto, AttributeDto> {
    
    /**
     * 次に実行するハンドラ．
     */
    private IBaseHandler<GetSidAttributeRequestDto, AttributeDto> nextHandler;
    
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
     * 空間IDの平面化を行う．
     * 
     * @param chainDto 埋設物情報取得へのリクエストに利用者システムIDを追加したDTO
     * @return 終端ハンドラからの返り値
     */
    @Override
    public AttributeDto handle(FacilityInfoChainDto chainDto) throws Exception {
        
        final List<String> flattenedSids = chainDto.getSids()
                .stream()
                .map(extrudedSid -> {
                    
                    String[] splitedSid = extrudedSid.split("/");
                    
                    splitedSid[1] = "0";
                    
                    return String.join("/", splitedSid);
                    
                })
                .collect(Collectors.toList());
        
        final GetSidAttributeRequestDto getSidAttributeRequestDto = GetSidAttributeRequestDto.builder()
                .sidList(flattenedSids)
                .isFlatSearch(true)
                .servicerId(chainDto.getServicerId())
                .infraCompanyIdList(chainDto.getInfraCompanyIds())
                .returnZoomLevel(chainDto.getReturnZoomLevel())
                .updateTime(chainDto.getUpdateDate())
                .build();

        
        return nextHandler.handle(getSidAttributeRequestDto);
        
    }
            
}
