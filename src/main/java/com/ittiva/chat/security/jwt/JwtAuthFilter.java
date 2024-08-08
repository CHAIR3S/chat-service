package com.ittiva.chat.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ittiva.chat.exception.NoAutorizadoException;
import com.ittiva.chat.exception.RegistroInexistenteException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{

    @Value("${academia.seguridad.habilitada}")
    private Boolean seguridadHabilitada; 


    private final JwtAuthenticationProvider jwtAuthenticationProvider;
	
	
    
    /**
     * Verifica si a la URL no se le debe aplicar el filtro
     * @param request current HTTP request Petición a validar
     * @return True la URI existe en la lista blanca, false de lo contrario
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {	
    	
		//Url que no se necesitan autenticar
	    List<String> urlsToSkip = List.of("/swagger-ui.html/**"
	    		, "/swagger-ui/**"
	    		, (seguridadHabilitada) ? "/auth/**" : "/**"
	    		, (request.getMethod().equals(HttpMethod.POST.toString())) ? "/usuario" : "/auth/**");

        String requestURI = request.getRequestURI();
        return urlsToSkip.stream().anyMatch(url -> new AntPathMatcher().match(url, requestURI));
    }
    
    
    
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//Obtener encabezado de autorizacion
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        

        
        //Si no tiene header
        if (header == null) {
        	log.error("No contiene headers");
            throw new NoAutorizadoException();
        }

        //Obtener token
        String[] authElements = header.split(" ");

        //Si token no tiene el formato correspondiente
        if (authElements.length != 2 || !"Bearer".equals(authElements[0])) {
        	log.error("No cumple formato JWT");
            throw new NoAutorizadoException();
        }

        
        try {
        	//Evaluar si token es real
            Authentication auth = jwtAuthenticationProvider.validateToken(authElements[1]);
            SecurityContextHolder.getContext().setAuthentication(auth);

            log.info("ContextHolder");
            log.debug(SecurityContextHolder.getContext().getAuthentication().toString());
            
        } catch (RuntimeException | RegistroInexistenteException e) {
            SecurityContextHolder.clearContext();
            log.error("Error obteniendo ContextHolder");
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);
	}
	

}
