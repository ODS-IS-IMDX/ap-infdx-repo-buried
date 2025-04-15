// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * 埋設物情報取得のレスポンスDTOを定義したクラス．<br>
 * 子DTOとして{@link AttributeDto}を保持する．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/21
 */
@Value
@AllArgsConstructor
@Builder
public class GetFacilityInfoResponseDto {
    
    /**
     * データモデルタイプ．
     */
    private String dataModelType;
    
    /**
     * 属性．
     */
    private AttributeDto attribute;
    
}
