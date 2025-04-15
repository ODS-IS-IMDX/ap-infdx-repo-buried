// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import java.math.BigDecimal;
import java.util.List;

import com.spatialid.app.api.constants.FacilityInfoConstants;
import com.spatialid.app.common.validation.CheckCrossFieldConditions;
import com.spatialid.app.common.validation.CheckPositive;
import com.spatialid.app.common.validation.CheckSearchArea;
import com.spatialid.app.common.validation.CheckSid;
import com.spatialid.app.common.validation.CheckSidArea;
import com.spatialid.app.common.validation.CheckSidZoomLevel;
import com.spatialid.app.common.validation.ConditionalMandatory;
import com.spatialid.app.common.validation.Groups.First;
import com.spatialid.app.common.validation.Groups.Second;
import com.spatialid.app.common.validation.Groups.Third;
import com.spatialid.app.common.validation.XorFields;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * 埋設物情報取得へのリクエストDTOを定義したクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/21
 */
@Value
@AllArgsConstructor
@Builder
@Jacksonized
@CheckCrossFieldConditions(
        xorFields = { @XorFields(firstFieldName = "sids", secondFieldName = "searchArea") },
        conditionalMandatories = { @ConditionalMandatory(conditionalField = "searchArea", mandatoryField = "epsgCode") })
public class GetFacilityInfoRequestDto {
    
    /**
     * 空間IDのリスト．
     */
    @CheckSidArea(lowerLimit = FacilityInfoConstants.ZL_LOWER_LIMIT, upperLimit = FacilityInfoConstants.ZL_UPPER_LIMIT,groups = {Third.class})
    private List<@CheckSid(groups = {First.class})
        @CheckSidZoomLevel(lowerLimit = FacilityInfoConstants.ZL_LOWER_LIMIT,
            upperLimit = FacilityInfoConstants.ZL_UPPER_LIMIT,groups = {Second.class}) String> sids;
    
    /**
     * 座標情報のリスト．
     */
    @CheckSearchArea
    private List<String> searchArea;
    
    /**
     * EPSGコード．
     */
    @DecimalMin(value = "1")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    private BigDecimal epsgCode;
    
    /**
     * インフラ事業者IDのリスト．
     */
    private List<String> infraCompanyIds;
    
    /**
     * 返却ズームレベル．
     */
    @CheckPositive
    private Integer returnZoomLevel;
    
    /**
     * 更新日時．
     */
    private String updateDate;
    
}
