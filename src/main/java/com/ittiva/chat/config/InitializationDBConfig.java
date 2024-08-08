package com.ittiva.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ittiva.chat.entity.Usuario;
import com.ittiva.chat.repository.UsuarioRepository;
import com.ittiva.chat.util.PasswordEncoderUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InitializationDBConfig {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	
	@Autowired
	PasswordEncoderUtil passwordEncoderUtil;
	

    @EventListener(ApplicationReadyEvent.class)
    public void iniciarUsuario() {
    	if(usuarioRepository.count() == 0) {
    		log.debug("Creando usuario admin");
    		
    		
    		Usuario usuario = new Usuario();
    		
    		usuario.setCorreo("admin@chat.com");
    		usuario.setContrasena(passwordEncoderUtil.encode("contrasena"));
    		usuario.setFoto(null);

    		usuarioRepository.save(usuario);
    		
    	}
    	
    	
    	
    }

}
