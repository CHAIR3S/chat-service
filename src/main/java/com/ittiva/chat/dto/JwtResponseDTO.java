package com.ittiva.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDTO {
	
	String jwt;
	
	UsuarioDTO usuario;

}
