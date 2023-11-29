package com.CrimsonBackendDatabase.crimsondb.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/api/v1/**")
                .allowedOrigins("https://crimson-recruitment.web.app","http://localhost:3000","https://crimsonrecruit.com")
                .allowedMethods("GET", "POST")
                .allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Methods", "Content-Type", "withCredentials")
                .allowCredentials(true).maxAge(3600);

        // Add more mappings...
    }
}