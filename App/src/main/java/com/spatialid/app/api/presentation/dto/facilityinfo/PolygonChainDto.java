// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.facilityinfo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * ポリゴン面積取得結果と埋設物情報取得APIのリクエストを合わせて保持するDTO．<br>
 * ポリゴン面積取得から座標→空間ID変換の処理へ渡される．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/12/02
 */
@Value
@AllArgsConstructor
@Builder
public class PolygonChainDto {
    
    /**
     * ポリゴン座標．
     */
    private String polygon;
    
    /**
     * 座標情報のリスト．
     */
    private List<String> searchArea;
    
    /**
     * EPSGコード．
     */
    private Integer epsgCode;
                
    /**
     * システム利用者ID．
     */
    private String servicerId;
    
    /**
     * インフラ事業者リスト．
     */
    private List<String> infraCompanyIds;

    /**
     * 更新日時．
     */
    private String updateDate;

    /**
     * 返却ズームレベル．
     */
    private Integer returnZoomLevel;
    
}
