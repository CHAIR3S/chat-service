package com.ittiva.chat.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensajeDTO {

	private Long idMensaje;
	
	private String texto;

	private LocalDateTime fecha;
	
	private String archivo;
	
	private UsuarioDTO usuario;

	private ChatDTO chat;
}
