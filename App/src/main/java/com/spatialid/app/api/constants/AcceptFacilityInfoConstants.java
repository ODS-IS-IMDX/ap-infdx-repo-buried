// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.constants;

import com.spatialid.app.common.constant.RestApiConstants;

/**
 * 埋設物情報取得受付API関連の定数を管理する定数クラス。
 * 
 * @author ukai jun
 * @version 1.1 2024/08/23
 * 
 */
public class AcceptFacilityInfoConstants {

    /** acceptFacilityInfoメソッドの URI */
    public static final String ACCEPT_URI = "/facility-output-task";

    /** 設備データ出力タスク登録のURI */
    public static final String OUTPUT_TASKS_URI = "/gen/api/task/v1/output-tasks";

    /** 設備データ出力タスク登録のAPI名 */
    public static final String OUTPUTTASKS_NAME = "設備データ出力タスク登録";

    /** 埋設物情報取得APIにおけるズームレベルの上限． */
    public static final int ZL_UPPER_LIMIT = 26;

    /** 埋設物情報取得APIにおけるズームレベルの下限． */
    public static final int ZL_LOWER_LIMIT = 13;

    /* SQSの利用で設定するメッセージグループID. */
    public static final String MESSAGE_GROUP_ID = "facility-message-group";
    
   
}