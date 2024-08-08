package com.ittiva.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ittiva.chat.dto.*;
import com.ittiva.chat.service.IChatService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IChatService chatService;

    @GetMapping
    public ResponseEntity<RespuestaDTO> obtenerTodos() {
        log.info("Obteniendo todos los chats");
        RespuestaDTO respuesta = chatService.obtener();
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Obteniendo chat con ID: {}", id);
        RespuestaDTO respuesta = chatService.obtenerPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<RespuestaDTO> obtenerPorUsuario(@PathVariable Long idUsuario) {
        log.info("Obteniendo chats por usuario con ID: {}", idUsuario);
        RespuestaDTO respuesta = chatService.obtenerPorUsuario(idUsuario);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<RespuestaDTO> crear(@RequestBody ChatDTO chatDTO) {
        log.info("Creando nuevo chat");
        RespuestaDTO respuesta = chatService.crea(chatDTO);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping
    public ResponseEntity<RespuestaDTO> actualizar(@RequestBody ChatDTO chatDTO) {
        log.info("Actualizando chat con ID: {}", chatDTO.getIdChat());
        RespuestaDTO respuesta = chatService.actualiza(chatDTO);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> eliminar(@PathVariable Long id) {
        log.info("Eliminando chat con ID: {}", id);
        RespuestaDTO respuesta = chatService.elimina(id);
        return ResponseEntity.ok(respuesta);
    }

}
