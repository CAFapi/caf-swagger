/*
 * Copyright 2019-2025 Open Text.
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
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

@Configuration
@ComponentScan("com.github.cafapi.swaggerui")
public class SwaggerUIConfig implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment env;

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
        if (annotationAttributes == null) {
            return;
        }
        final String contractPackage = (String) annotationAttributes.get("value");
        if (!StringUtils.hasLength(contractPackage)) {
            return;
        }
        Integer adminPort = env.getProperty("management.server.port", Integer.class);
        final String contractPath = "classpath:/" + contractPackage.replace(".", "/") + "/";
        beanDefinitionRegistry.registerBeanDefinition("swaggerUIController",
                BeanDefinitionBuilder
                        .genericBeanDefinition(SwaggerUIController.class)
                        .addConstructorArgValue(contractPath)
                        .addConstructorArgValue(adminPort != null ? adminPort : 0)
                        .setScope("singleton")
                        .getBeanDefinition());
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
