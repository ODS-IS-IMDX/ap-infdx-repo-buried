// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.accept;

import java.math.BigDecimal;
import java.util.List;

import com.spatialid.app.api.constants.AcceptFacilityInfoConstants;
import com.spatialid.app.common.constant.RestApiConstants;
import com.spatialid.app.common.validation.CheckSid;
import com.spatialid.app.common.validation.CheckSidArea;
import com.spatialid.app.common.validation.CheckSidZoomLevel;
import com.spatialid.app.common.validation.Groups.First;
import com.spatialid.app.common.validation.Groups.Forth;
import com.spatialid.app.common.validation.Groups.Second;
import com.spatialid.app.common.validation.Groups.Third;
import com.spatialid.app.common.validation.ValidDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.groups.Default;
import lombok.Data;

/**
 * 埋設物情報取得受付にてクライアント側から受け取るrequest。
 * 
 * @author ukai jun
 * @version 1.1 2024/08/22
 */
@Data
public class AcceptFacilityInfoRequestDto {

    /** 空間ID */
    @NotEmpty(groups = {Third.class})
    @CheckSidArea(lowerLimit = AcceptFacilityInfoConstants.ZL_LOWER_LIMIT,
                  upperLimit = AcceptFacilityInfoConstants.ZL_UPPER_LIMIT,
                  groups = {Forth.class})
    private List<
    @NotEmpty(groups = {Default.class})
    @CheckSid(groups = {First.class})
    @CheckSidZoomLevel(lowerLimit = AcceptFacilityInfoConstants.ZL_LOWER_LIMIT,
                       upperLimit = AcceptFacilityInfoConstants.ZL_UPPER_LIMIT,
                       groups = {Second.class})
                String> sidList;

    /** インフラ事業者ID */
    private List<String> infraCompanyIdList;

    /** 返却ズームレベル */
    @DecimalMin(value = "1", groups = {First.class})
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, groups = {Second.class})
    private BigDecimal returnZoomLevel;

    /** 更新日時 */
    @ValidDate(pattern = RestApiConstants.STRICT_DATE_FORMAT)
    private String updateDate;

}