// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * 空間・属性情報参照へのリクエストDTOを定義したクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Value
@AllArgsConstructor
@Builder
public class GetSidAttributeRequestDto {
    
    /**
     * 空間IDのリスト．
     */
    private List<String> sidList;

    /**
     * 平面検索フラグ．
     */
    @JsonProperty("isFlatSearch")
    private Boolean isFlatSearch;
    
    /**
     * システム利用者ID．
     */
    private String servicerId;
    
    /**
     * インフラ事業者リスト．
     */
    private List<String> infraCompanyIdList;

    /**
     * 更新日時．
     */
    private String updateTime;

    /**
     * 返却ズームレベル．
     */
    private Integer returnZoomLevel;

}
