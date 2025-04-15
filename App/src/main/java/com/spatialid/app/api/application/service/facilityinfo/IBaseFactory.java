// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.service.facilityinfo;

import com.spatialid.app.api.presentation.dto.facilityinfo.AttributeDto;
import com.spatialid.app.api.presentation.dto.facilityinfo.FacilityInfoChainDto;

/**
 * 埋設物情報取得におけるハンドラクラスのチェーン生成を提供するインターフェース．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
public interface IBaseFactory {
    
    /**
     * チェーン生成を提供する．
     * 
     * @param type 生成するチェーンのタイプ
     * @return チェーンが構成された始点ハンドラ
     */
    public IBaseHandler<FacilityInfoChainDto, AttributeDto> createHandlerChain(String type) throws Exception;
    
}
