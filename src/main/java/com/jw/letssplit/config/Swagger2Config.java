package com.jw.letssplit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jw.letssplit.controller"))
                .paths(PathSelectors.any())
                .build()
                // .securitySchemes(securitySchemes())
                // .securityContexts(securityContexts())
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SwaggerUI-demo")
                .description("Let's Split")
                .contact(new Contact("jw", "my-url", "my-email"))
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextList = new ArrayList<>();
        securityContextList.add(getContextByPath("/something/.*"));
        securityContextList.add(getContextByPath("/secret/.*"));
        return securityContextList;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
                new AuthorizationScope("global", "accessEverything")
        };
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferenceList;
    }
}
