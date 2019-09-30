package com.example.university.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.university")).paths(any()).build()
                .apiInfo(new ApiInfo("University API's ",
                        "API's to register students and courses", "2.0", null,
                        new Contact("Remote Developer","https://www.linkedin.com/in/luis-salvatierra-44a9b295/", "venomont@gmail.com"),
                        null, null, new ArrayList()));
    }
}
