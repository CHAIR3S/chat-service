package com.ittiva.chat.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ittiva.chat.security.jwt.JwtAuthFilter;
import com.ittiva.chat.security.jwt.JwtAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    
    
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	
    /**
     * Bean de JwtAuthFilter para inyeccion
     * @return Implementación JwtAuthFilter
     */
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtAuthenticationProvider);
    }

}
