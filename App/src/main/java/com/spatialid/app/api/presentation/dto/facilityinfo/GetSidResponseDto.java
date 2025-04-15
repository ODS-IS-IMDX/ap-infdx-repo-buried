// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * 座標→空間ID変換のレスポンスDTOを定義したクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Value
@AllArgsConstructor
@Builder
@Jacksonized
public class GetSidResponseDto {
    
    /**
     * 空間IDのリスト．
     */
    private List<String> sidList;
    
}
