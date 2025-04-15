// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * 埋設物の情報とその空間IDを保持するクラス．<br>
 * {@link GetFacilityInfoResponseDto}を親DTOとする．<br>
 * {@link FacilitySidDto}を子DTOとして保持する．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/22
 */
@Value
@AllArgsConstructor
@Builder
@JsonPropertyOrder({ "isFacilityExist", "facilitySidList" })
public class AttributeDto {
    
    /**
     * 埋設物存否．
     */
    @JsonProperty("isFacilityExist")
    private boolean isFacilityExist;
    
    /**
     * 設備空間情報リスト．
     */
    private List<FacilitySidDto> facilitySidList;
    
    /**
     * 埋設物存否を返却するGetter．<br>
     * このフィールドは、lombokでGetterを自動生成するとフィールド名が正しく出力されないため作成した．
     * 
     * @return 埋設物存否
     */
    @JsonGetter("isFacilityExist")
    public boolean isFacilityExist() {
        
        return this.isFacilityExist;
        
    }
    
}
