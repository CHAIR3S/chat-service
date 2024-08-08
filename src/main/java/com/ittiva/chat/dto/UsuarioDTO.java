package com.ittiva.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private Long idUsuario;
	
	private String correo;
	
	private String contrasena;
	
	private String nombre;
	
	private String foto;

}
