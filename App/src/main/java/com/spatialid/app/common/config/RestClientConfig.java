// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.web.client.RestClientBuilderConfigurer;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spatialid.app.common.property.RestClientProperty;
import com.spatialid.app.common.property.secrets.RestClientSecretsProperty;

/**
 * RestClientの設定を行うクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/18
 */
@Configuration
public class RestClientConfig {
    
    /**
     * 機密ではないRestClientの設定を保持するクラス．
     */
    private final RestClientProperty restClientProperty;
    
    /**
     * RestClient関連のシークレット情報を保持するクラス．
     */
    private final RestClientSecretsProperty restClientSecretsProperty;
    
    public RestClientConfig(RestClientSecretsProperty restClientSecretsProperty,
            RestClientProperty restClientProperty) {
        
        this.restClientSecretsProperty = restClientSecretsProperty;
        this.restClientProperty = restClientProperty;
        
    }
    
    /**
     * RestClientのビルダーをBeanに登録する．
     * <p>
     * 以下の設定を行う．<br>
     *  タイムアウト<br>
     *  4xx, 5xxのレスポンスを受け取った際に例外を送出しない<br>
     *  日付を分解しない
     * </p>
     * 
     * @param configurer {@link RestClientBuilderConfigurer}
     * @return 設定済みのビルダー
     */
    @Bean
    public RestClient.Builder customRestClient(RestClientBuilderConfigurer configurer) {

        ClientHttpRequestFactorySettings setting = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(restClientProperty.getConnectionTimeout()))
                .withReadTimeout(Duration.ofSeconds(restClientProperty.getReadTimeout()));
        
        RestClient.Builder builder = RestClient.builder()
                .defaultStatusHandler(
                        status -> true
                        , (request, response) -> {})
                .requestFactory(ClientHttpRequestFactories.get(setting));

        configurer.configure(builder);

        return builder;

    }
        
    /**
     * API利用権限APIの呼び出しについて基本的な設定を行う．
     * <p>
     * API利用権限APIの基本URLを設定する．
     * </p>
     * 
     * @param restClientBuilder {@link RestClient.Builder}
     * @return API利用権限の設定を保持したRestClient
     * @throws JsonProcessingException シークレット情報とプロパティクラスのマッピングに失敗した場合
     */
    @Bean
    public RestClient authorityApiClient(RestClient.Builder restClientBuilder) throws JsonProcessingException {
        
        StringBuilder uri = new StringBuilder();
        
        uri.append(restClientProperty.getProtocol())
            .append("://")
            .append(restClientSecretsProperty.getAuthorityDomain())
            .append(":")
            .append(restClientProperty.getPort());

        RestClient restClient = restClientBuilder.baseUrl(uri.toString()).build();
        
        return restClient;

    }
    
    /**
     * 業務共通APIの呼び出しについて基本的な設定を行う．
     * <p>
     * 業務共通APIの基本URLを設定する．
     * </p>
     * 
     * @param restClientBuilder {@link RestClient.Builder}
     * @return 業務共通設定を保持したRestClient
     * @throws JsonProcessingException シークレット情報とプロパティクラスのマッピングに失敗した場合
     */
    @Bean
    public RestClient commonApiClient(RestClient.Builder restClientBuilder) throws JsonProcessingException {
        
        StringBuilder uri = new StringBuilder();
        
        uri.append(restClientProperty.getProtocol())
            .append("://")
            .append(restClientSecretsProperty.getCommonDomain())
            .append(":")
            .append(restClientProperty.getPort());

        RestClient restClient = restClientBuilder.baseUrl(uri.toString()).build();
        
        return restClient;
        
    }
    
    /**
     * 3D空間ID共通APIの呼び出しについて基本的な設定を行う．
     * <p>
     * 3D空間ID共通APIの基本URLを設定する．
     * </p>
     * 
     * @param restClientBuilder {@link RestClient.Builder}
     * @return 3D空間ID共通設定を保持したRestClient
     * @throws JsonProcessingException シークレット情報とプロパティクラスのマッピングに失敗した場合
     */
    @Bean
    public RestClient spatialIdApiClient(RestClient.Builder restClientBuilder) throws JsonProcessingException {
        
        StringBuilder uri = new StringBuilder();
        
        uri.append(restClientProperty.getProtocol())
            .append("://")
            .append(restClientSecretsProperty.getSpatialIdDomain())
            .append(":")
            .append(restClientProperty.getPort());

        RestClient restClient = restClientBuilder.baseUrl(uri.toString()).build();
        
        return restClient;
        
    }
    
    /**
     * アクセス履歴APIの呼び出しについて基本的な設定を行う．
     * <p>
     * アクセス履歴APIの基本URLを設定する．<br>
     * アクセス履歴APIはSSL通信を行うため、プロトコル/ポートをその他APIとは別に設定する．
     * </p>
     * 
     * @param restClientBuilder
     * @return アクセス履歴の設定を保持したRestClient
     * @throws JsonProcessingException
     */
    @Bean
    RestClient accessHistoryApiClient(RestClient.Builder restClientBuilder) throws JsonProcessingException {
        
        final StringBuilder uri = new StringBuilder();
        
        uri.append(restClientProperty.getSslProtocol())
            .append("://")
            .append(restClientSecretsProperty.getAccessHistoryDomain())
            .append(":")
            .append(restClientProperty.getSslPort());
        
        return restClientBuilder.baseUrl(uri.toString()).build();
        
    }
    
}
