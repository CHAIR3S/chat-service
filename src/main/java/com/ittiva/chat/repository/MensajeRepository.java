package com.ittiva.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ittiva.chat.entity.Chat;
import com.ittiva.chat.entity.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long>{
	

	public List<Mensaje> findByChat(Chat chat);

}
