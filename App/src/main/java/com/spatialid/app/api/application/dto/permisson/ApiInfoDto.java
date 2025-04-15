// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.api.application.dto.permisson;

import lombok.Data;

/**
 * API利用権限情報 apiAuthDtoのフィールドの一つ。
 * 
 * @author ukai jun
 * @version 1.1 2024/08/29
 */
@Data
public class ApiInfoDto {

    /** APIID */
    private String apiId;

    /** URI */
    private String uri;

}
