package biblioteca_spring.config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("Biblioteca API").version("1.0").description("API rest para gerenciamento de uma biblioteca com JWT"))
                .servers(List.of(
                        new Server().url("https://sistema-biblioteca-api-spring-production.up.railway.app").description("Servidor de Produção (Railway)"),
                        new Server().url("http://localhost:8080").description("Servidor Local")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authetication")).components(new Components()
                        .addSecuritySchemes("Bearer Authentication", new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .scheme("bearer").bearerFormat("JWT").in(SecurityScheme.In.HEADER).name("Authorization")));
    }


}
