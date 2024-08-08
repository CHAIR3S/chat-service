package com.ittiva.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ittiva.chat.dto.AuthUserDTO;
import com.ittiva.chat.dto.RespuestaDTO;
import com.ittiva.chat.exception.ContrasenaIncorrectaException;
import com.ittiva.chat.service.IAuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	IAuthService service;
    
    @PostMapping("")
    public ResponseEntity<RespuestaDTO> iniciarSesion (
    		@RequestBody AuthUserDTO userAuthDTO){
    	RespuestaDTO respuesta = new RespuestaDTO();

    	log.debug("Inicio de autenticacion");
    	
    	try{
			respuesta = service.autenticar(userAuthDTO); 
        	
        	log.debug(respuesta.getMensaje());
    	}catch ( ContrasenaIncorrectaException e) {
    		log.error("Error, contraseña incorrecta");
    		respuesta.setEstatus("0");
    		respuesta.setMensaje("Error, contraseña incorrecta");
    		respuesta.setLista(null);
    		respuesta.setObject(null);
    	}
    	
    	return "1".equals(respuesta.getEstatus()) ? ResponseEntity.ok(respuesta) : ResponseEntity.badRequest().body(respuesta);
    }
    
    

    
    @GetMapping("/valida")
    public ResponseEntity<RespuestaDTO> validateToken (@RequestHeader("Authorization") String authHeader){
    	RespuestaDTO respuesta = new RespuestaDTO();
        
        String[] authElements = authHeader.split(" ");
        
        String token = authElements[1];
    	
    	respuesta = service.validaToken(token);
    	
    	return "1".equals(respuesta.getEstatus()) ? ResponseEntity.ok(respuesta) : ResponseEntity.badRequest().body(respuesta);
    }
    
}
