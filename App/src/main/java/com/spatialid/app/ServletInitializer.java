// © 2025 NTT DATA Japan Co., Ltd. & NTT InfraNet All Rights Reserved.

package com.spatialid.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * 
 * @author ukai jun
 * @version 1.1 2024/08/23
 */
public class ServletInitializer extends SpringBootServletInitializer {

    public ServletInitializer() {
    }

    /**
     * 
     * 
     * @param application アプリケーションビルダー
     * @return SpringApplicationBuilder アプリケーションビルダー
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(InfraExternalApiApplication.class);
    }

}