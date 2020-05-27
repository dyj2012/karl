package com.karl.boot.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Think
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {
    @Bean(value = "karlApi")
    @Order(value = 1)
    public Docket karlRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("接口文档")
                .description("<div style='font-size:14px;color:red;'>接口文档微服务2.0版本</div>")
                .termsOfServiceUrl("http://www.demo.com/")
                .contact(new Contact("", "", ""))
                .version("1.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .groupName("karl-doc")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.karl.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
