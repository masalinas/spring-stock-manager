package io.oferto.application.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
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
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

@Configuration
public class SwaggerConfig {
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
        Map<String, Object> additionalQueryStringParams=new HashMap<>();
        additionalQueryStringParams.put("nonce", "123456");

        return SecurityConfigurationBuilder.builder()
            .clientId("stock-manager")
            .realm("stock-manager")
            .appName("swagger-ui")
            .additionalQueryStringParams(additionalQueryStringParams)
            .build();
    }
	
    private List<SecurityContext> buildSecurityContext() {
        List<SecurityReference> securityReferences = new ArrayList<>();

        securityReferences.add(SecurityReference.builder().reference("oauth2").scopes(scopes().toArray(new AuthorizationScope[]{})).build());

        SecurityContext context = SecurityContext.builder().forPaths(Predicates.alwaysTrue()).securityReferences(securityReferences).build();

        List<SecurityContext> ret = new ArrayList<>();
        ret.add(context);
        
        return ret;
    }
	
    private List<SecurityScheme> buildSecurityScheme() {
        List<SecurityScheme> lst = new ArrayList<>();
        // lst.add(new ApiKey("api_key", "X-API-KEY", "header"));

        LoginEndpoint login = new LoginEndpointBuilder().url("http://localhost:8081/auth/realms/stock-manager/protocol/openid-connect/auth").build();

        List<GrantType> gTypes = new ArrayList<>();
        gTypes.add(new ImplicitGrant(login, "acces_token"));

        lst.add(new OAuth("oauth2", scopes(), gTypes));
        return lst;
    }
    
    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> scopes = new ArrayList<>();
        
        for (String scopeItem : new String[]{"openid=openid", "profile=profile"}) {
            String scope[] = scopeItem.split("=");
            if (scope.length == 2) {
                scopes.add(new AuthorizationScopeBuilder().scope(scope[0]).description(scope[1]).build());
            } else {
                //log.warn("Scope '{}' is not valid (format is scope=description)", scopeItem);
            }
        }

        return scopes;
    }  
}