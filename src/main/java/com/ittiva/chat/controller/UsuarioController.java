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

import com.ittiva.chat.dto.RespuestaDTO;
import com.ittiva.chat.dto.UsuarioDTO;
import com.ittiva.chat.service.IUsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<RespuestaDTO> obtenerTodos() {
        log.info("Obteniendo todos los usuarios");
        RespuestaDTO respuesta = usuarioService.obtener();
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> obtenerPorId(@PathVariable Long id) {
        log.info("Obteniendo usuario con ID: {}", id);
        RespuestaDTO respuesta = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<RespuestaDTO> obtenerPorEmail(@PathVariable String email) {
        log.info("Obteniendo usuario por email: {}", email);
        RespuestaDTO respuesta = usuarioService.obtenerPorEmail(email);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<RespuestaDTO> crear(@RequestBody UsuarioDTO usuarioDTO) {
        log.info("Creando nuevo usuario");
        RespuestaDTO respuesta = usuarioService.crea(usuarioDTO);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping
    public ResponseEntity<RespuestaDTO> actualizar(@RequestBody UsuarioDTO usuarioDTO) {
        log.info("Actualizando usuario con ID: {}", usuarioDTO.getIdUsuario());
        RespuestaDTO respuesta = usuarioService.actualiza(usuarioDTO);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaDTO> eliminar(@PathVariable Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        RespuestaDTO respuesta = usuarioService.elimina(id);
        return ResponseEntity.ok(respuesta);
    }

}
