package com.shirzad.pointOfSaleSystem.configuration;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.AuthorizeHttpRequestsDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.sql.Array;
import java.util.Arrays;

@Configuration
public class SecurityConfig {
    // Security configuration class for the application. It defines security settings and rules such as authentication and authorization.
    // All Security-related beans and configurations are typically defined in this class.
    // Beans like SecurityFilterChain are created here to manage security aspects of the application.
    // Other beans related to authentication providers, password encoders, etc., can also be defined in this class.
    // the list of most important beans in security configuration class are:
    // 1. SecurityFilterChain: this chain defines how security is applied to HTTP requests. for example:
        // - which endpoints require authentication
        // - which roles are needed to access certain resources
        // - session management policies (like stateless sessions)
    // 2. AuthenticationManager: responsible for processing authentication requests.
        // It verifies user credentials and establishes the security context. for example:
        // - it can use different authentication providers (like in-memory, database, LDAP, etc.)
        // - it can be customized to include additional authentication mechanisms
        //  - it is often used in conjunction with login endpoints to authenticate users
    // 3. PasswordEncoder: used to encode and verify passwords securely.
        // It ensures that passwords are stored in a hashed format rather than plain text. for example:
        // - it can use different encoding algorithms (like BCrypt, SCrypt, etc.)
        // - it is essential for protecting user credentials in the database
    // 4. UserDetailsService: provides user-specific data during authentication.
        // It loads user details (like username, password, roles) from a data source. for example:
        // - it can fetch user information from a database or an external service
        // - it is used by the AuthenticationManager to validate user credentials
    // 5. JwtAuthenticationFilter (if using JWT for authentication): a custom filter that processes JWT tokens in incoming requests.
        // It extracts and validates the token to authenticate users. for example:
        // - it checks the validity of the JWT token
        // - it sets the security context based on the token's claims
    // 6. CorsConfigurationSource (if configuring CORS): defines CORS settings for the application.
        // CORS (Cross-Origin Resource Sharing) is a security feature that allows or restricts resources on a
        // web server to be requested from another domain outside the domain from which the resource originated.
        // It specifies which origins, methods, and headers are allowed in cross-origin requests. for example:
        // - it can restrict access to certain domains
        // - it can allow specific HTTP methods (GET, POST, etc.) for cross-origin requests
    // 7. OAuth2AuthorizedClientService (if using OAuth2): manages OAuth2 authorized clients.
        // It handles the storage and retrieval of OAuth2 client details. for example:
        // - it can store access tokens and refresh tokens
        // - it is used in OAuth2 authentication flows

    @Bean
    // Define the security filter chain for the application.
    // SecurityFilterChain is a core component of Spring Security that defines how security is applied to HTTP requests.
    // It allows you to configure various security aspects such as authentication, authorization, session management, and more.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Configure session management to be stateless, meaning no sessions will be created or used by Spring Security.
        // what is session? a way to store user data between different requests.
        // think of it as a temporary storage for user information in a web application.
        // By setting the session creation policy to STATELESS, we ensure that each request is treated independently,
        // without relying on any stored session data.
        return http.sessionManagement(management->
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set up authorization rules for incoming HTTP requests.
                .authorizeHttpRequests(Authorize->
                        Authorize
                                // Specify that any request to endpoints under "/api/**" must be authenticated.
                                .requestMatchers("/api/**").authenticated()
                                // Specify that any request to endpoints under "/api/super-admin/**" must have the "ADMIN" role.
                                .requestMatchers("/api/super-admin/**").hasRole("ADMIN")
                                // Allow all other requests without any authentication or authorization.
                                .anyRequest().permitAll()
                // add
                ).addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)

                .csrf(AbstractHttpConfigurer::disable)
                    .cors(
                            cors->cors.configurationSource(corsConfigurationSource())
                    ).build();
    }

    private CorsConfigurationSource corsConfigurationSource() {

        return new CorsConfigurationSource(){
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(
                        Arrays.asList(
                            "http://localhost:5173",
                            "http://localhost:3000"
                        )
                );
                cfg.setAllowedMethods(
                        Arrays.asList(
                                "GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "OPTIONS"
                        )
                );
                cfg.setAllowedHeaders(
                        Arrays.asList(
                                "Authorization",
                                "Content-Type"
                        )
                );
                cfg.setAllowCredentials(true);

                return cfg;

            }
        };
    }
}