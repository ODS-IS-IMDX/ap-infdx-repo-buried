// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.facilityinfo;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.spatialid.app.api.constants.FacilityInfoConstants;
import com.spatialid.app.api.presentation.dto.facilityinfo.AttributeDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.FacilityInfoChainDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.GetSidAttributeRequestDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.PolygonChainDto;

/**
 * 埋設物情報取得におけるハンドラクラスのチェーン生成を行うクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Service
public class FacilityInfoChainFactory implements IBaseFactory {
    
    /**
     * ポリゴン面積取得のハンドラクラス．
     */
    private final IBaseHandler<FacilityInfoChainDto, AttributeDto> polygonHandler;
    
    /**
     * 座標→空間ID変換のハンドラクラス．
     */
    private final IBaseHandler<PolygonChainDto, AttributeDto> sidHandler;
    
    /**
     * 空間ID平面化のハンドラクラス．
     */
    private final IBaseHandler<FacilityInfoChainDto, AttributeDto> flattenHandler;
    
    /**
     * 空間・属性情報参照のハンドラクラス．
     */
    private final IBaseHandler<GetSidAttributeRequestDto, AttributeDto> sidAttributeHandler;
    
    public FacilityInfoChainFactory(@Qualifier("polygonHandler") IBaseHandler<FacilityInfoChainDto, AttributeDto> polygonHandler,
            @Qualifier("sidHandler") IBaseHandler<PolygonChainDto, AttributeDto> sidHandler,
            @Qualifier("flattenHandler") IBaseHandler<FacilityInfoChainDto, AttributeDto> flattenHandler,
            @Qualifier("sidAttributeHandler") IBaseHandler<GetSidAttributeRequestDto, AttributeDto> sidAttributeHandler) {
        
        this.polygonHandler = polygonHandler;
        this.sidHandler = sidHandler;
        this.flattenHandler = flattenHandler;
        this.sidAttributeHandler = sidAttributeHandler;
        
    }
    
    /**
     * 引数の値に基づいたチェーンを生成して返却する．
     * <p>
     * 引数のtypeは、空間IDの直接指定か取得対象領域による指定かによって決定される．<br>
     * {@link FacilityInfoConstants#POLYGON_TYPE}<br>
     * {@link FacilityInfoConstants#SID_TYPE}
     * </p>
     * 
     * @param type 生成するチェーンのタイプ
     * @return チェーンが構成された始点ハンドラ
     */
    @Override
    public IBaseHandler<FacilityInfoChainDto, AttributeDto> createHandlerChain(String type) throws Exception {
        
        if (FacilityInfoConstants.POLYGON_TYPE.equals(type)) {
            
            polygonHandler.setNext(sidHandler).setNext(sidAttributeHandler);
            
            return polygonHandler;
            
        } else {
            
            flattenHandler.setNext(sidAttributeHandler);
            
            return flattenHandler;
            
        }
        
    }
        
}
