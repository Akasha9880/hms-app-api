package com.hmsapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
        private JwtFilter jwtFilter;
        public SecurityConfig(JwtFilter jwtFilter) {
            this.jwtFilter = jwtFilter;
        }
    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
)throws Exception
    {

       http.csrf().disable().cors().disable();
       http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
       http.authorizeHttpRequests().anyRequest().permitAll();
//        http.authorizeHttpRequests()
//                .requestMatchers("/api/auth/sign-up","/api/auth/login","/api/auth/property/sign-up","/api/v1/property/{searchParam}")
//               .permitAll()
//                .requestMatchers("/api/v1/review?propertyId=1","/api/v1/review/user/reviews","/api/auth")
//                .hasRole("USER")
//                .requestMatchers("/api/v1/property/addProperty","/api/v1/Country/addCountry","/api/v1/city/addCity","/api/v1/City/delete","/api/v1/Country/delete")
//                .hasRole("OWNER")
//                .requestMatchers("/api/v1/property/deleteProperty")
//                .hasAnyRole("OWNER","ADMIN")
//               .requestMatchers("/api/auth/blog/sign-up")
//                .hasRole("ADMIN")
//               .anyRequest().authenticated();
       return http.build();
    }
}
