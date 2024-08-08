package com.ittiva.chat.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
	
	private Long idChat;
	
	private String nombre;
	
	private LocalDateTime fecha;

	private UsuarioDTO usuarioA;
	
	private UsuarioDTO usuarioB;

}
