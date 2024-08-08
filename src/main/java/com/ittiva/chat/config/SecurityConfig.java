package com.ittiva.chat.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ittiva.chat.exception.AccessDeniedHandlerException;
import com.ittiva.chat.security.jwt.JwtAuthFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que configura lo relacionado a las peticiones HTTP
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
	
    @Value("${academia.seguridad.habilitada}")
    private Boolean seguridadHabilitada; 

    private final AccessDeniedHandlerException accessDeniedHandlerException;

    private final JwtAuthFilter jwtAuthFilter;
    
    private String path;
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	path="/**";
    	
    	if(seguridadHabilitada) {
    		path="/auth/**";
    	}
    	
    	log.info("jwt.security {}: " + seguridadHabilitada);
    	
    	
        http
                .cors(withDefaults())
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerException)
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->
                        requests
                        		.requestMatchers(path, "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        		
                        		.requestMatchers(HttpMethod.POST, "/usuario").permitAll()
                        		
                        		
                                .anyRequest().authenticated()

                );


        return http.build();
    }
    
    
    
    //Habilita CrossOrigin para todas las rutas
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("https://academia-sable.vercel.app"));
        configuration.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
	
	
	

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
