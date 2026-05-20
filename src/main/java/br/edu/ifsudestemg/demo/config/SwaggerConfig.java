package br.edu.ifsudestemg.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("POSTOBACK API")
                        .description("API do Sistema de Gestão de Postos Combustíveis")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Allan Mahisson, Pedro Faria, Marco Gabriel")
                                .url("https://github.com/omahisson/postoBack")
                                .email("mahisson@icloud.com")
                        )
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("postoback")
                .packagesToScan("br.edu.ifsudestemg.demo.api.controller")
                .build();
    }

}