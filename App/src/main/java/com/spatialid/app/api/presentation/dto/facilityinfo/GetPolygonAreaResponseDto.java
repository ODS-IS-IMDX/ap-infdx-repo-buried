// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import com.spatialid.app.api.constants.FacilityInfoConstants;
import com.spatialid.app.common.validation.CheckPolygonArea;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * ポリゴン面積取得のレスポンスDTOを定義したクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Value
@AllArgsConstructor
@Builder
@Jacksonized
public class GetPolygonAreaResponseDto {
    
    /**
     * ポリゴン面積．
     */
    @CheckPolygonArea(limit = FacilityInfoConstants.POLYGON_AREA_LIMIT)
    private double area;
    
}
