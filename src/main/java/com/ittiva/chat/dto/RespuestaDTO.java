package com.ittiva.chat.dto;

import java.util.List;

import lombok.Data;

@Data
public class RespuestaDTO {
	/**ESTATUS*/
	protected String estatus;
	/**MENSAJE*/
	protected String mensaje;
	/**LISTA*/
	protected List<?> lista;
	/**OBJECT*/
	protected Object object;
}
