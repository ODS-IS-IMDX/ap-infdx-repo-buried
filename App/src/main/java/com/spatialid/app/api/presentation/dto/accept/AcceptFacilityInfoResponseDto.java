// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.presentation.dto.accept;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * 埋設物属性情報取得受付にてクライアント側に渡す情報を扱うResponse。
 * 
 * @author ukai jun
 * @version 1.1 2024/08/22
 */
@Data
public class AcceptFacilityInfoResponseDto {

    /** データモデルタイプ */
    private String dataModelType;

    /** 属性 */
    public Map<String, String> attribute = new HashMap<String, String>();

}