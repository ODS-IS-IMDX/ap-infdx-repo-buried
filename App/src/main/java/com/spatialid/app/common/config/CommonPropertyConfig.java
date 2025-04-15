// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.spatialid.app.common.property.RestClientProperty;

import lombok.NoArgsConstructor;

/**
 * プロパティファイルの読み込みを行うクラス．
 * 
 * @author matsumoto kentaro
 * @version 1.1 2024/10/08
 */
@Configuration
@PropertySources({@PropertySource("classpath:${SPRING_PROFILES_ACTIVE}/application-${SPRING_PROFILES_ACTIVE}.properties"),
    @PropertySource("classpath:${SPRING_PROFILES_ACTIVE}/api-${SPRING_PROFILES_ACTIVE}.properties")})
@EnableConfigurationProperties({RestClientProperty.class})
@NoArgsConstructor
public class CommonPropertyConfig {
    
}
