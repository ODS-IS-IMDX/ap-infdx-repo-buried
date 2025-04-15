// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * ハンドラの入力となるDTOクラス．<br>
 * リクエストと利用者システムIDをまとめて保持する．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/25
 */
@Value
@AllArgsConstructor
@Builder
public class FacilityInfoChainDto {
    
    /**
     * 空間IDのリスト．
     */
    private List<String> sids;
    
    /**
     * 利用者システムID．
     */
    private String servicerId;
    
    /**
     * 座標情報のリスト．
     */
    private List<String> searchArea;
    
    /**
     * EPSGコード．
     */
    private Integer epsgCode;
    
    /**
     * インフラ事業者IDのリスト．
     */
    private List<String> infraCompanyIds;
    
    /**
     * 返却ズームレベル．
     */
    private Integer returnZoomLevel;
    
    /**
     * 更新日時．
     */
    private String updateDate;
    
}
