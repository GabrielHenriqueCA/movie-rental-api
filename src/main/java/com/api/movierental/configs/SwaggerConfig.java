package com.api.movierental.configs;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI instance for the project.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie Rental API")
                        .description("API for a movie rental service")
                        .version("1.0.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact()
                                .name("Gabriel Henrique C.A")
                                .url("https://github.com/GabrielHenriqueCA")
                                .email("gabrielhcacontatO@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github")
                        .url("https://github.com/GabrielHenriqueCA/movie-rental-api"));
    }


    /**
     * Adds default API responses to the given operation.
     */
    public void addDefaultResponses(Operation operation) {
        ApiResponses apiResponses = operation.getResponses();
        apiResponses.addApiResponse("200", createApiResponse("Success!"));
        apiResponses.addApiResponse("201", createApiResponse("Object persisted!"));
        apiResponses.addApiResponse("204", createApiResponse("Object deleted!"));
        apiResponses.addApiResponse("400", createApiResponse("Request error!"));
        apiResponses.addApiResponse("401", createApiResponse("Unauthorized access!"));
        apiResponses.addApiResponse("403", createApiResponse("Forbidden access!"));
        apiResponses.addApiResponse("404", createApiResponse("Object not found!"));
        apiResponses.addApiResponse("500", createApiResponse("Application error!"));
    }

    /**
     * Creates an API response with the given message.
     */
    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}
