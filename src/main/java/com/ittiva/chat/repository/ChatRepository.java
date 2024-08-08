package com.ittiva.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ittiva.chat.entity.Chat;
import com.ittiva.chat.entity.Usuario;


public interface ChatRepository extends JpaRepository<Chat, Long>{


    @Query("SELECT c FROM Chat c WHERE c.usuarioA.id = :idUsuario OR c.usuarioB.id = :idUsuario")
    List<Chat> findChatsByUsuarioId(@Param("idUsuario") Long idUsuario);
	
}
