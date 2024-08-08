package com.ittiva.chat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ittiva.chat.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByCorreo(String correo);

}
