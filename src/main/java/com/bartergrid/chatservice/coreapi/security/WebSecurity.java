package com.bartergrid.chatservice.coreapi.security;

import com.bartergrid.core.security.JwtAuthorities.AuthorizationFilter;
import com.bartergrid.core.security.JwtAuthorities.JwtDecoderConfig;
import com.bartergrid.core.security.JwtAuthorities.KeycloakJwtAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration class for web security settings.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurity {
    @Autowired
    private Environment environment;

    @Autowired
    private AuthorizationFilter authorizationFilter;

    @Value("${security.permit-all}")
    private String permitAllEndpoints;

    @Value("${security.restricted}")
    private String restrictedEndpoints;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Bean
    public JwtDecoder jwtDecoder() {
        // Use the centralized JwtDecoderConfig from core library
        return JwtDecoderConfig.createJwtDecoder(jwkSetUri, environment);
    }

    public WebSecurity(Environment environment, AuthorizationFilter authorizationFilter) {
        this.environment = environment;
        this.authorizationFilter = authorizationFilter;

        // Validate critical security component
        if (authorizationFilter == null) {
            String errorMsg = "CRITICAL SECURITY ERROR: AuthorizationFilter is null during WebSecurity initialization";
            log.error(errorMsg);
            throw new IllegalStateException(errorMsg);
        }
    }

    /*    @Bean
    public JwtAuthenticationConverterBean jwtAuthenticationConverterApp() {
        return new JwtAuthenticationConverterBean(environment);
    }*/

    @Bean
    public KeycloakJwtAuthenticationConverter keycloakJwtAuthenticationConverter() {
        return new KeycloakJwtAuthenticationConverter();
    }

    /**
     * Configures the security filter chain for the application.
     * <p>
     * This method sets up various security configurations including:
     * - Disabling CSRF protection
     * - Setting up permitted endpoints based on application properties
     * - Configuring authentication requirements
     * - Setting session management policy
     * - Disabling HTTP Basic and form-based authentication
     * - Configuring frame options for headers
     *
     * @param http The HttpSecurity object to be configured
     * @return A fully configured SecurityFilterChain
     * @throws Exception If an error occurs during configuration
     */
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // Configure CSRF Token Inclusion in Response Headers
        http.csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
        );

        // Configure session management once
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Configure OAuth2 resource server (authentication)
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwtAuthenticationConverter(keycloakJwtAuthenticationConverter())));

        // Add custom JWT Authorization Filter - No need for null check since we validate in constructor
        http.addFilterBefore(authorizationFilter,
                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        log.info("Successfully added AuthorizationFilter to the filter chain");

        String[] permitAllEndpoints = this.permitAllEndpoints.split(",");
        String[] restrictedEndpoints = this.restrictedEndpoints.split(",");

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    for (String endpoint : permitAllEndpoints) {
                        if (endpoint != null && !endpoint.trim().isEmpty()) {
                            authorize.requestMatchers(new AntPathRequestMatcher(endpoint.trim())).permitAll();
                        }
                    }

                    for (String endpoint : restrictedEndpoints) {
                        if (endpoint != null && !endpoint.trim().isEmpty()) {
                            authorize.requestMatchers(new AntPathRequestMatcher(endpoint.trim()))
                                    .access((authentication, object) -> {
                                        // First check if authentication is present
                                        if (authentication.get() == null) {
                                            log.error("Authentication not present for restricted endpoint: {}", endpoint.trim());
                                            return new AuthorizationDecision(false);
                                        }

                                        boolean hasScope = authentication.get().getAuthorities().stream()
                                                .anyMatch(a -> a.getAuthority().trim().equals("SCOPE_bartergrid-order-service") ||
                                                        a.getAuthority().trim().equals("bartergrid-order-service"));
                                        boolean hasRole = authentication.get().getAuthorities().stream()
                                                .anyMatch(a -> (a.getAuthority().trim().equals("ROLE_ADMIN") || a.getAuthority().trim().equals("ADMIN")) ||
                                                        (a.getAuthority().trim().equals("ROLE_bartergrid-user") ||
                                                                a.getAuthority().trim().equals("bartergrid-user")) || (a.getAuthority().trim().equals("ROLE_bartergrid-app") ||
                                                        a.getAuthority().trim().equals("bartergrid-app")));

                                        // Log authorization decisions for debugging
                                        if (!hasScope || !hasRole) {
                                            log.error("Access denied for user {} - hasScope: {}, hasRole: {}",
                                                    authentication.get().getName(), hasScope, hasRole);
                                        }

                                        return new AuthorizationDecision(hasScope && hasRole);
                                    });
                        }
                    }

                    authorize.anyRequest().authenticated();
                });

        http.httpBasic(httpBasic -> httpBasic.disable());
        http.formLogin(formLogin -> formLogin.disable());

        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));
        return http.build();
    }

}
