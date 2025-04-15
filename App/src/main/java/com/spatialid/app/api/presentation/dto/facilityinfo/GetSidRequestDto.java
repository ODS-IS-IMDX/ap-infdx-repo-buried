// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * 座標→空間ID変換へのリクエストDTOを定義したクラス．
 */
@Value
@AllArgsConstructor
@Builder
public class GetSidRequestDto {
    
    /**
     * ポリゴン座標．
     */
    private String polygon;
    
    /**
     * EPSGコード．
     */
    @JsonProperty("epsg")
    private int epsgCode;
    
    /**
     * ズームレベル．
     */
    private int zoomLevel;
    
}
