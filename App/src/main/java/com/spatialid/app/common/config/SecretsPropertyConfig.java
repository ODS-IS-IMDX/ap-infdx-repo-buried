// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spatialid.app.common.aws.AwsSecretManager;
import com.spatialid.app.common.property.secrets.RestClientSecretsProperty;
import com.spatialid.app.common.property.secrets.SqsClientSecretsProperty;

/**
 * シークレット情報を保持するオブジェクトをBean登録する設定クラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/18
 */
@Configuration
public class SecretsPropertyConfig {
    
    /**
     * シークレット名．
     */
    private final String secretName;
    
    public SecretsPropertyConfig(@Value("${cloud.aws.secretmanager.secretname}") String secretName) {
        
        this.secretName = secretName;
        
    }
        
    /**
     * APIの接続情報のうち機密部分を取得してBean登録を行う．
     * 
     * @return 機密情報が格納されたプロパティクラス．
     * @throws Exception
     */
    @Bean
    public RestClientSecretsProperty restClientSecretsProperty() throws Exception {
        
        final RestClientSecretsProperty restClientSecretsProperty = AwsSecretManager.getSecretsValue(secretName, RestClientSecretsProperty.class);
        
        return restClientSecretsProperty;
        
    }    
    
    @Bean
    public SqsClientSecretsProperty sqsClientSecretsProperty() throws Exception {
        
        final SqsClientSecretsProperty sqsClientSecretsProperty = AwsSecretManager.getSecretsValue(secretName, SqsClientSecretsProperty.class);
        
        return sqsClientSecretsProperty;
        
    } 
    
}
