// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * ポリゴン面積取得へのリクエストDTOを定義したクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Value
@AllArgsConstructor
@Builder
public class GetPolygonAreaRequestDto {
    
    /**
     * ポリゴン座標．
     */
    private String polygon;
    
    /**
     * EPSGコード．
     */
    @JsonProperty("epsg")
    private int epsgCode;
    
}
