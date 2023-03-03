/*
 * Copyright 2019-2022 Micro Focus or one of its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cafapi.swaggerui;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

public class SwaggerUIController implements WebMvcConfigurer {

    private final int adminPort;
    private final String contractPath;

    public SwaggerUIController(final String contractPath, final int adminPort) {
        this.contractPath = contractPath;
        this.adminPort = adminPort;
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:./swagger/");
        registry.addViewController("/swagger").setViewName("redirect:./swagger/");
        registry.addViewController("/swagger-ui").setViewName("redirect:./swagger/");
        registry.addViewController("/swagger-ui/").setViewName("redirect:/swagger/");

        registry.addViewController("/swagger/").setViewName("forward:/swagger/index.html");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/swagger/**")
                .addResourceLocations(
                        "classpath:/swagger/",
                        contractPath,
                        "classpath:/META-INF/resources/webjars/microfocus-swagger-ui-dist/1.2.0/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        if (adminPort > 0) {
            registry.addInterceptor(new SwaggerInterceptor(adminPort));
        }
    }
}
