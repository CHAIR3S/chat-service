package com.ittiva.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ittiva.chat.dto.AuthUserDTO;
import com.ittiva.chat.dto.RespuestaDTO;
import com.ittiva.chat.dto.UsuarioDTO;
import com.ittiva.chat.exception.ContrasenaIncorrectaException;
import com.ittiva.chat.security.jwt.JwtAuthenticationProvider;
import com.ittiva.chat.util.PasswordEncoderUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService implements IAuthService {
	
	@Autowired
	IUsuarioService userService;
	
	@Autowired
	PasswordEncoderUtil passwordEncoderUtil;

    private final JwtAuthenticationProvider authProvider;
    
    public AuthService(JwtAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }
	
	@Override
	public RespuestaDTO autenticar(AuthUserDTO credenciales) throws ContrasenaIncorrectaException{
		RespuestaDTO respuesta = new RespuestaDTO();
		
		respuesta = userService.obtenerPorEmail(credenciales.getCorreo());
		
		//Si el usuario no fue encontrado por el email
		if("0".equals(respuesta.getEstatus())) {
			log.error(respuesta.getMensaje());
			return respuesta;
		}
		
		UsuarioDTO userEncontrado = (UsuarioDTO) respuesta.getObject();
		
		//Si las contraseñas no coinciden
		if(!passwordEncoderUtil.matches(credenciales.getContrasena(), userEncontrado.getContrasena())) {
			log.error("Contraseña no coincide con el correo");
			throw new ContrasenaIncorrectaException();
		}
		
		respuesta = authProvider.createToken(userEncontrado);
				
		return respuesta;
	}
	

//	@Override
//	public RespuestaDTO autenticarGoogle(AuthUserDTO credenciales) {
//		RespuestaDTO respuesta = new RespuestaDTO();
//		GoogleIdToken idToken = null;
//		
//		respuesta = userService.obtenerPorEmail(credenciales.getCorreo());
//		
//		//Si el usuario no fue encontrado por el email
//		if("0".equals(respuesta.getEstatus())) {
//			log.info("Guardar usuario google");
//			log.info(credenciales.getGoogleJWT());
//			
//			try {
//				idToken = verifyGoogleToken(credenciales.getGoogleJWT());
//				log.info("=========== SIGNATURE ==============");
//			} catch (GeneralSecurityException e) {
//				log.error(e.getMessage());
//			} catch (IOException e) {
//				log.error(e.getMessage());
//			}
//
//
//	        if (idToken != null) {
//	            Payload payload = idToken.getPayload();
//
//	            // Obteniendo información del usuario desde el payload
//	            String name = (String) payload.get("name");
//	            String pictureUrl = (String) payload.get("picture");
//
//	            // Utiliza los datos obtenidos como sea necesario
//	            log.info("Email: " + credenciales.getCorreo());
//	            log.info("Nombre: " + name);
//	            log.info("URL de imagen: " + pictureUrl);
//
//			
//				UsuarioDTO usuario = new UsuarioDTO();
//				usuario.setCorreo(credenciales.getCorreo());
//				usuario.setNombre(name);
//				usuario.setFoto(pictureUrl);
//				
//				UsuarioDTO usuariobd = (UsuarioDTO) userService.crea(usuario).getObject();
//				
//				usuariobd.setGoogleJWT(credenciales.getGoogleJWT());
//	
//				respuesta = authProvider.createToken(usuariobd);
//	        }
//				
//			return respuesta;
//		}
//		
//		UsuarioDTO userEncontrado = (UsuarioDTO) respuesta.getObject();
//		
//		//Si las contraseñas no coinciden
////		if(!passwordEncoderUtil.matches(credenciales.getContrasena(), userEncontrado.getContrasena())) {
////			log.error("Contraseña no coincide con el correo");
////			throw new ContrasenaIncorrectaException();
////		}
//		
//		userEncontrado.setGoogleJWT(credenciales.getGoogleJWT());
//		respuesta = authProvider.createToken(userEncontrado);
//				
//		return respuesta;
//	}
	
	
//	private GoogleIdToken verifyGoogleToken (String googleToken) throws GeneralSecurityException, IOException {
//
//	    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//	    NetHttpTransport transport = new NetHttpTransport();
//	    
//		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//			    // Specify the CLIENT_ID of the app that accesses the backend:
//			    .setAudience(Collections.singletonList("604629377452-9fqbfs9mmkld6i1be05snhnonv18vfb0.apps.googleusercontent.com"))
//			    // Or, if multiple clients access the backend:
//			    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
//			    .build();
//
//			// (Receive idTokenString by HTTPS POST)
//		log.info("verifyyy");
//
//		GoogleIdToken idToken = verifier.verify(googleToken);
//			
//		return (idToken != null) ? idToken : null;
//	}

	

	@Override
	public RespuestaDTO validaToken (String token) {
		
		RespuestaDTO respuesta = new RespuestaDTO();
		
		respuesta = authProvider.validaToken(token);
		
		return respuesta;
	}
	
}
