package com.ittiva.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ittiva.chat.dto.ChatDTO;
import com.ittiva.chat.dto.RespuestaDTO;
import com.ittiva.chat.dto.UsuarioDTO;
import com.ittiva.chat.entity.Chat;
import com.ittiva.chat.repository.ChatRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatService implements IChatService{

    @Autowired
    private ChatRepository repository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UsuarioService usuarioService;

    ChatDTO convertToDTO(Chat chat) {
        return modelMapper.map(chat, ChatDTO.class);
    }

    

    Chat convertToEntity(ChatDTO chatDTO) {
        return modelMapper.map(chatDTO, Chat.class);
    }

	@Override
	public RespuestaDTO obtener() {
        RespuestaDTO respuesta = new RespuestaDTO();
        List<Chat> chats = repository.findAll();
        List<ChatDTO> chatDTOs = new ArrayList<>();

        chats.forEach(chat -> {
            chatDTOs.add(convertToDTO(chat));
        });

        log.info("Chats encontrados -> " + chats.size());

        respuesta.setEstatus("1");
        respuesta.setMensaje("Chats encontrados correctamente");
        respuesta.setObject(null);
        respuesta.setLista(chatDTOs);

        return respuesta;
	}

	@Override
	public RespuestaDTO obtenerPorId(Long id) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Optional<Chat> chatOptional = repository.findById(id);

        if (!chatOptional.isPresent()) {
            log.error("Chat no encontrado por ID : " + id);
            respuesta.setEstatus("0");
            respuesta.setMensaje("No se encontró el chat por ID");
            respuesta.setObject(null);
            respuesta.setLista(null);

            return respuesta;
        }

        Chat chatFound = chatOptional.get();
        log.debug("getChatById returns {} ", chatFound.toString());

        respuesta.setEstatus("1");
        respuesta.setMensaje("Chat encontrado por ID");
        respuesta.setObject(convertToDTO(chatFound));
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO elimina(Long id) {
        RespuestaDTO respuesta = new RespuestaDTO();
        if (!repository.existsById(id)) {
            log.error("Chat no encontrado");
            respuesta.setEstatus("0");
            respuesta.setMensaje("Error, chat no encontrado");
            respuesta.setLista(null);
            respuesta.setObject(null);

            return respuesta;
        }

        repository.deleteById(id);
        respuesta.setEstatus("1");
        respuesta.setMensaje("Chat eliminado correctamente");

        return respuesta;
	}

	@Override
	public RespuestaDTO crea(ChatDTO chatDTO) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Chat chatToCreate = convertToEntity(chatDTO);
        Chat chatCreated = repository.save(chatToCreate);

        respuesta.setEstatus("1");
        respuesta.setObject(convertToDTO(chatCreated));
        respuesta.setMensaje("Chat creado correctamente");
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO actualiza(ChatDTO chatDTO) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Optional<Chat> chatBd = repository.findById(chatDTO.getIdChat());

        if (!chatBd.isPresent()) {
            log.error("Error, chat no encontrado");
            respuesta.setEstatus("0");
            respuesta.setMensaje("Error, no se encontró el chat para actualizar");
            respuesta.setObject(null);
            respuesta.setLista(null);

            return respuesta;
        }

        Chat chatToUpdate = convertToEntity(chatDTO);
        Chat chatUpdated = repository.save(chatToUpdate);

        log.info("Chat actualizado correctamente");

        respuesta.setEstatus("1");
        respuesta.setMensaje("Chat actualizado correctamente");
        respuesta.setObject(convertToDTO(chatUpdated));
        respuesta.setLista(null);

        return respuesta;
	}

	@Override
	public RespuestaDTO obtenerPorUsuario(Long idUsuario) {
	    RespuestaDTO respuesta = new RespuestaDTO();
	    UsuarioDTO usuarioDTO = new UsuarioDTO();
	    
	    respuesta = usuarioService.obtenerPorId(idUsuario);
	    
	    if("0".equals(respuesta.getEstatus())) {
	    	return respuesta;
	    }
	    
	    usuarioDTO = (UsuarioDTO) respuesta.getObject();
	    
	    List<Chat> chats = repository.findChatsByUsuarioId(usuarioDTO.getIdUsuario());
	    List<ChatDTO> chatDTOs = new ArrayList<>();

	    if (chats.isEmpty()) {
	        log.warn("No se encontraron chats para el usuario con ID: " + usuarioDTO.getIdUsuario());
	        respuesta.setEstatus("0");
	        respuesta.setMensaje("No se encontraron chats para el usuario especificado");
	        respuesta.setObject(null);
	        respuesta.setLista(null);
	        return respuesta;
	    }

	    chats.forEach(chat -> {
	        chatDTOs.add(convertToDTO(chat));
	    });

	    log.info("Chats encontrados para el usuario -> " + usuarioDTO.getIdUsuario() + ": " + chats.size());
	    respuesta.setEstatus("1");
	    respuesta.setMensaje("Chats encontrados correctamente para el usuario");
	    respuesta.setObject(null);
	    respuesta.setLista(chatDTOs);

	    return respuesta;
	}

}
