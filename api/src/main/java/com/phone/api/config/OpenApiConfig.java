package com.phone.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Phone List API",
        version = "1.0",
        description = "API for managing phone lists",
        contact = @Contact(
            name = "Ömer ALTAN",
            email = "omeraltan66@gmail.com",
            url = "https://omeraltan.com/course"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(
            description = "Local ENV",
            url = "http://localhost:8080"
        ),
        @Server(
            description = "PROD ENV",
            url = "https://omeraltan.com/course"
        )
    }
)
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI baseOpenAPI(){
        ApiResponse badRequest = new ApiResponse()
            .content(new Content()
                .addMediaType("application/json", new MediaType()
                    .addExamples("default", new Example()
                        .value("{\"code\" : 400, \"status\" : \"Bad Request\", \"Message\" : \"Bad Request\"}"))));

        ApiResponse internalServerError = new ApiResponse()
            .content(new Content()
                .addMediaType("application/json", new MediaType()
                    .addExamples("default", new Example()
                        .value("{\"code\" : 500, \"status\" : \"Internal Server Error\", \"Message\" : \"Internal Server Error\"}}"))));

        ApiResponse successfulResponse = new ApiResponse()
            .content(new Content()
                .addMediaType("application/json", new MediaType()
                    .addExamples("default", new Example()
                        .value("{\"name\" : \"string\",\"surname\" : \"string\",\"age\" : 0}"))));

        ApiResponse okResponse = new ApiResponse()
            .description("Request processed successfully");

        ApiResponse notFoundResponse = new ApiResponse()
            .description("Resource not found");

        ApiResponse createdResponse = new ApiResponse()
            .description("Resource created successfully");

        ApiResponse forbiddenResponse = new ApiResponse()
            .description("Access forbidden");

        Components components = new Components();
        components.addResponses("badRequest", badRequest);
        components.addResponses("internalServerError", internalServerError);
        components.addResponses("successfulResponse", successfulResponse);
        components.addResponses("okResponse", okResponse);
        components.addResponses("notFoundResponse", notFoundResponse);
        components.addResponses("createdResponse", createdResponse);
        components.addResponses("forbiddenResponse", forbiddenResponse);

        return new OpenAPI().components(components);
    }


}
