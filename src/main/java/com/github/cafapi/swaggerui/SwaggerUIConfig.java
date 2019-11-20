/*
 * Copyright 2015-2018 Micro Focus or one of its affiliates.
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

import com.github.cafapi.CAFSwaggerUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

@Configuration
@ComponentScan("com.github.cafapi.swaggerui")
public class SwaggerUIConfig implements ImportBeanDefinitionRegistrar {

    @Value("${management.server.port:0}")
    private int adminPort = 0;

    /**
     * Implement the ImportBeanDefinitionRegistrar interface to be able to pull out the values from the annotation that caused this
     * import.
     *
     * @see CAFSwaggerUI
     */
    @Override
    public void registerBeanDefinitions(final AnnotationMetadata annotationMetadata,
                                        final BeanDefinitionRegistry beanDefinitionRegistry) {
        final String annotationName = CAFSwaggerUI.class.getName();
        final AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                annotationMetadata.getAnnotationAttributes(annotationName, false)
        );
        if (annotationAttributes == null || StringUtils.isEmpty(annotationAttributes.get("value"))) {
            return;
        }
        String contractPath = "classpath:/" + ((String) annotationAttributes.get("value")).replace(".", "/") + "/";
        beanDefinitionRegistry.registerBeanDefinition("swaggerUIController",
                BeanDefinitionBuilder
                        .genericBeanDefinition(SwaggerUIController.class)
                        .addConstructorArgValue(contractPath)
                        .addConstructorArgValue(adminPort)
                        .setScope("singleton")
                        .getBeanDefinition());
    }
}
