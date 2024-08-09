package com.ittiva.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ittiva.chat.dto.MensajeDTO;
import com.ittiva.chat.dto.RespuestaDTO;
import com.ittiva.chat.service.IMensajeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
//@RequestMapping("/mensaje")
public class MensajeController {

    @Autowired
    private IMensajeService mensajeService;

//    @GetMapping
//    public ResponseEntity<RespuestaDTO> obtenerTodos() {
//        log.info("Obteniendo todos los mensajes");
//        RespuestaDTO respuesta = mensajeService.obtener();
//        return ResponseEntity.ok(respuesta);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<RespuestaDTO> obtenerPorId(@PathVariable Long id) {
//        log.info("Obteniendo mensaje con ID: {}", id);
//        RespuestaDTO respuesta = mensajeService.obtenerPorId(id);
//        return ResponseEntity.ok(respuesta);
//    }
//
//    @GetMapping("/chat/{idChat}")
//    public ResponseEntity<RespuestaDTO> obtenerPorChat(@PathVariable Long idChat) {
//        log.info("Obteniendo mensajes por chat con ID: {}", idChat);
//        RespuestaDTO respuesta = mensajeService.obtenerPorChat(idChat);
//        return ResponseEntity.ok(respuesta);
//    }

    @MessageMapping("/chat1")
    @SendTo("topic/canal1")
    public RespuestaDTO crear(MensajeDTO mensajeDTO) {
        log.info("Creando nuevo mensaje ", mensajeDTO.toString());
//        RespuestaDTO respuesta = mensajeService.crea(mensajeDTO);
//        return ResponseEntity.ok(respuesta);
        return new RespuestaDTO();
    }

//    @PutMapping
//    public ResponseEntity<RespuestaDTO> actualizar(@RequestBody MensajeDTO mensajeDTO) {
//        log.info("Actualizando mensaje con ID: {}", mensajeDTO.getIdMensaje());
//        RespuestaDTO respuesta = mensajeService.actualiza(mensajeDTO);
//        return ResponseEntity.ok(respuesta);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<RespuestaDTO> eliminar(@PathVariable Long id) {
//        log.info("Eliminando mensaje con ID: {}", id);
//        RespuestaDTO respuesta = mensajeService.elimina(id);
//        return ResponseEntity.ok(respuesta);
//    }

}
