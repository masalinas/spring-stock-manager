package io.oferto.application.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.LoginEndpointBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ImplicitGrant;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

@Configuration
public class SwaggerConfiguration {
	@Bean
	public Docket postsApi() {		
		return new Docket(DocumentationType.SWAGGER_2)				
			.apiInfo(apiInfo())
			.select().apis(RequestHandlerSelectors.basePackage("io.oferto.application.backend.controller")).paths(PathSelectors.any())                          
	        .build()
	        .securitySchemes(buildSecurityScheme())
	        .securityContexts(buildSecurityContext());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Stock Manager API")
			.description("Stock Manager API reference for developers")
			.termsOfServiceUrl("http://oferto.io/terms")
			.contact(new Contact("Oferto", "http://oferto.io", "info@oferto.io"))
			.build();
	}
	
	@Bean
    public SecurityConfiguration securityConfiguration() {
        Map<String, Object> additionalQueryStringParams = new HashMap<>();
        additionalQueryStringParams.put("nonce", "123456");

        return SecurityConfigurationBuilder.builder()
            .realm("stock-manager")
            .clientId("stock-manager")
            .appName("Stock Manager")
            .additionalQueryStringParams(additionalQueryStringParams)
            .build();
    }
	
    private List<SecurityContext> buildSecurityContext() {
        List<SecurityReference> securityReferences = new ArrayList<>();
        
        securityReferences.add(SecurityReference.builder().reference("oauth2").scopes(scopes().toArray(new AuthorizationScope[]{})).build());

        List<SecurityContext> securityContexts = new ArrayList<>();

        SecurityContext context = new SecurityContextBuilder().operationSelector(Predicates.alwaysTrue()).securityReferences(securityReferences).build();       
        securityContexts.add(context);
        
        return securityContexts;
    }
	
    private List<SecurityScheme> buildSecurityScheme() {
    	String swaggerHost = "localhost";
    	
    	System.out.println("HOST_SWAGGER: " + System.getenv("HOST_SWAGGER"));
    	
        if (System.getenv("HOST_SWAGGER") != null)
        	swaggerHost = System.getenv("HOST_SWAGGER");        	       
        
        List<SecurityScheme> securitySchemes = new ArrayList<>();
                
        LoginEndpoint login = new LoginEndpointBuilder().url("http://" + swaggerHost + ":8080/auth/realms/stock-manager/protocol/openid-connect/auth").build();
        
        List<GrantType> gTypes = new ArrayList<>();
        gTypes.add(new ImplicitGrant(login, "acces_token"));

        securitySchemes.add(new OAuth("oauth2", scopes(), gTypes));
        
        return securitySchemes;
    }
    
    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> scopes = new ArrayList<>();
        
        scopes.add(new AuthorizationScope("profile", "Profile user")); 
        scopes.add(new AuthorizationScope("email", "User email"));

        return scopes;
    }  
}