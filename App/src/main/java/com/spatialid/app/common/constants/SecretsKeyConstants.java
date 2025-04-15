// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.constants;

/**
 * AWS Secrets Managerのキー名を定義した定数クラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/18
 */
public class SecretsKeyConstants {
        
    /**
     * API利用権限コンテナのドメインに対応したキー名．
     */
    public static final String AUTHORITY_DOMAIN = "ECS-AUTHORITY-DOMAIN";
    
    /**
     * 業務共通コンテナのドメインに対応したキー名．
     */
    public static final String COMMON_DOMAIN = "ECS-COMMON-DOMAIN";
    
    /**
     * 3D空間ID共通コンテナのドメインに対応したキー名．
     */
    public static final String SPATIALID_DOMAIN = "ECS-3D-SPATIALID-DOMAIN";

    /**
     * アクセス履歴コンテナのドメインに対応したキー名．
     */
    public static final String AC_HISTORY_DOMAIN = "ECS-ACCESS-HISTORY-DOMAIN";

    /**
     * SQSでメッセージ送信先となるキューURL．
     */
    public static final String SQS_URL = "SQS-URL";

}
