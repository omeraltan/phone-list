package com.phone.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers(
//                    "/api/v1/auth/**",
//                    "/v2/api-docs",
//                    "/v3/api-docs",
//                    "/v3/api-docs/**",
//                    "/api/district/**",
//                    "/api/customer/**",
//                    "/swagger-resources",
//                    "/swagger-resources/**",
//                    "/configuration/ui",
//                    "/configuration/security",
//                    "/swagger-ui/**",
//                    "/webjars/**",
//                    "/swagger-ui.html"
//                ).permitAll()
//                .anyRequest().authenticated()
//            )
//            .build();
//    }

}
