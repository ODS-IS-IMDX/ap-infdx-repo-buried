// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spatialid.app.common.mapper.PrivateAttributeDeserializer;
import com.spatialid.app.common.mapper.PrivateAttributeSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * 空間・属性情報参照の結果を保持するクラス．<br>
 * {@link AttributeDto}を親DTOとする．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/22
 */
@Value
@AllArgsConstructor
@Builder
@Jacksonized
public class FacilitySidDto {
    
    /**
     * データ種別．
     */
    private String dataType;
    
    /**
     * 設備属性情報．
     */
    @JsonSerialize(nullsUsing = PrivateAttributeSerializer.class)
    @JsonDeserialize(using = PrivateAttributeDeserializer.class)
    private Map<String, String> facilityAttribute;
    
    /**
     * インフラ事業者ID．
     */
    private String infraCompanyId;
    
    /**
     * オブジェクトID．
     */
    private String objectId;
        
    /**
     * 設備種別名．
     */
    @JsonSetter("facilityClassificationName")
    private String objectName;

    /**
     * 空間IDリスト．
     */
    private List<String> sidList;    
    
}
