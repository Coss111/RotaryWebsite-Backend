package com.rotarywebsite.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class OAuth2Config {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Public endpoints
                .requestMatchers("/", "/public/**", "/api/auth/**", "/error").permitAll()
                .requestMatchers("/api/files/**").permitAll()
                
                // OAuth2 endpoints
                .requestMatchers("/oauth2/**", "/login/**").permitAll()
                
                // Member endpoints
                .requestMatchers("/api/members/**", "/api/projects/**", "/api/news/**").hasAnyRole("MEMBER", "ADMINISTRATOR")
                
                // Admin only endpoints
                .requestMatchers("/api/users/**", "/api/reports/**").hasRole("ADMINISTRATOR")
                
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/oauth2/authorization/github")
                .defaultSuccessUrl("/api/user/profile", true)
                .failureUrl("/public/auth-error")
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/public/logout-success")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}