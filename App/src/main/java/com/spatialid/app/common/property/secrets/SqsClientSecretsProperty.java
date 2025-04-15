// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.property.secrets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spatialid.app.common.aws.BaseSecretsValue;
import com.spatialid.app.common.constants.SecretsKeyConstants;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * SqsClientのシークレット情報を保持するクラス．
 * 
 * @author ukai jun
 * @version 1.1 2024/10/18
 */
@Value
@EqualsAndHashCode(callSuper = true)
@Builder
@Jacksonized
public class SqsClientSecretsProperty extends BaseSecretsValue {

    /**
     * SQSでメッセージ送信先となるキューURL.
     */
    @JsonProperty(SecretsKeyConstants.SQS_URL)
    private final String queueUrl;
}