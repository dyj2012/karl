package com.karl.boot.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author karl
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfiguration {
    @Bean(value = "karl-core")
    @Order(1)
    public Docket coreRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("karl-core")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.karl.core"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean(value = "karl-module")
    @Order(2)
    public Docket moduleRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("karl-module")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.karl.module"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("服务接口文档")
                .description("<div style='font-size:14px;color:red;'>REST API文档1.0版本</div>")
                .termsOfServiceUrl("http://127.0.0.1:8080/")
                .contact(new Contact("杜永军", "https://github.com/dyj2012/karl-parent", ""))
                .version("1.0")
                .build();
    }
}
