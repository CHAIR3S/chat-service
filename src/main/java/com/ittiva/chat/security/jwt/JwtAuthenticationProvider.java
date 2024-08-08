package com.ittiva.chat.security.jwt;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ittiva.chat.dto.JwtResponseDTO;
import com.ittiva.chat.dto.RespuestaDTO;
import com.ittiva.chat.dto.UsuarioDTO;
import com.ittiva.chat.exception.RegistroInexistenteException;
import com.ittiva.chat.service.IUsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationProvider {
	
	@Lazy
	@Autowired
	IUsuarioService userService;
	

    /**
     * Clave para cifrar el jwt
     */
    @Value("${jwt.secret}")
    private String clave;
    

    /**
     * Tiempo de expiracion en segundos
     */
	@Value("${jwt.expiration}")
	int expiracion;
	
    

    /**
     * Crea un nuevo jwt con el rol en los claims
     * @param UserDTO usuario para crear token
     * @return respuesta con Jwt creado y usuario
     */
    public RespuestaDTO createToken(UsuarioDTO usuarioDTO) {
    	RespuestaDTO respuesta = new RespuestaDTO();
    	
    	
    	
    	log.debug("Creando token");

        Date now = new Date();
        Date validity = new Date(now.getTime() + (1000 * expiracion)); // En milisegundos

        Algorithm algorithm = Algorithm.HMAC256(clave);

        String tokenCreated = JWT.create()
                .withClaim("idUsuario", usuarioDTO.getIdUsuario())
                .withClaim("nombre", usuarioDTO.getNombre())
                .withClaim("correo", usuarioDTO.getCorreo())
                
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
        
        
        respuesta.setEstatus("1");
        respuesta.setMensaje("Token creado correctamente");
        respuesta.setObject(new JwtResponseDTO(tokenCreated, usuarioDTO));
        respuesta.setLista(null);
        

        return respuesta;
    }
    
    

    /**
     * Valida si el token es valido y retorna una sesión del usuario
     * @param token Token a validar
     * @return Sesion del usuario
     * @throws RegistroInexistenteException Si el usuario ya no existe
     * @throws CredentialsExpiredException Si el token ya expiró
     */
    public Authentication validateToken(String token) throws RegistroInexistenteException {
    	RespuestaDTO respuestaUser = new RespuestaDTO();
        HashSet<SimpleGrantedAuthority> rolesAndAuthorities = new HashSet<>();

    	log.debug("Validando token: {} " + token);

        //verifica el token como su firma y expiración, lanza una excepcion si algo falla
        JWT.require(Algorithm.HMAC256(clave)).build().verify(token);


        //Una vez sido el token validado
        DecodedJWT jwt = JWT.decode(token);
        
        Map<String, Claim> claims = jwt.getClaims(); // Obtener mapa de los claims
        
        Claim idUser = claims.get("idUsuario");
        
        String idUserString = idUser.toString();
        
        
        //Buscar usuario por id
        respuestaUser = userService.obtenerPorId(Long.parseLong(idUserString));
        
        //Si no existe hay error
        if("0".equals(respuestaUser.getEstatus())) {
        	log.error("No se encontro usuario con id: " + idUserString);
        	throw new RegistroInexistenteException(idUserString);
        }
        
        
        UsuarioDTO userEncontrado = (UsuarioDTO) respuestaUser.getObject();
        
        

        return new UsernamePasswordAuthenticationToken(userEncontrado, token, rolesAndAuthorities);
    }
    
    


    /**
     * Valida si el token fue firmado por el algoritmo del servicio
     * @param token a ser validado
     * @return RespuestaDTO si el token es válido o inválido
     */
    public RespuestaDTO validaToken(String token) {

    	RespuestaDTO respuesta = new RespuestaDTO();
    	Boolean valido = false;
    	
        //verifica el token como su firma y expiración, lanza una excepcion si algo falla
		try {
	        JWT.require(Algorithm.HMAC256(clave)).build().verify(token);
		} catch (JWTVerificationException e) {

			respuesta.setEstatus("0");
			respuesta.setMensaje("Error al validar token: " + e.getMessage());
			respuesta.setObject(valido);
			return respuesta;
		}
        
        
        
        respuesta.setEstatus("1");
        respuesta.setMensaje("Éxito, el token es válido");
        valido = true;
        respuesta.setObject(valido);
    
        return respuesta;
    }
    
    
	

}
