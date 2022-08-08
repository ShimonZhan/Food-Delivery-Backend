package uk.ac.soton.food_delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @Author: ZhanX
 * @Date: 2020-12-26 15:51:12
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    private static final List<String> excludedPathRegex = Arrays.asList(
            "/error.*",
            "/admin/.*"
    );


    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(webApiInfo())
                .securitySchemes(Collections.singletonList(HttpAuthenticationScheme.JWT_BEARER_BUILDER
                        .name("JWT")
                        .build()))
                .securityContexts(Collections.singletonList(SecurityContext.builder()
                        .securityReferences(Collections.singletonList(SecurityReference.builder()
                                .scopes(new AuthorizationScope[0])
                                .reference("JWT")
                                .build()))
                        // 声明作用域
                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                        .build()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("uk.ac.soton.food_delivery.controller"))
                .paths(s -> {
                            for (String pathRegex : excludedPathRegex) {
                                if (s.matches(pathRegex)) {
                                    return false;
                                }
                            }
                            return true;
                        })
                .build();

    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("Food Delivery Backend Api Document")
                .description("This document describes the Food Delivery interface definition.")
                .version("1.0")
                .contact(new Contact("Xinming Zhan", "", "xz5y21@soton.ac.uk"))
                .contact(new Contact("Yaoqi Zhu", "", ""))
                .contact(new Contact("Zeyu Zhao", "", ""))
                .build();
    }
}
