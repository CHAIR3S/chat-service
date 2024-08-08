package com.ittiva.chat.service;

import com.ittiva.chat.dto.AuthUserDTO;
import com.ittiva.chat.dto.RespuestaDTO;
import com.ittiva.chat.exception.ContrasenaIncorrectaException;

public interface IAuthService {
	
	public RespuestaDTO autenticar(AuthUserDTO credenciales) throws ContrasenaIncorrectaException;

	public RespuestaDTO validaToken(String token);

//	RespuestaDTO autenticarGoogle(AuthUserDTO credenciales);

}
