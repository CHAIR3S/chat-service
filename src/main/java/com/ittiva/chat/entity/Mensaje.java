package com.ittiva.chat.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mensaje")
public class Mensaje implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_mensaje")
	private Long idMensaje;
	
	private String texto;

    @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime fecha;

	@Lob
	private String archivo;    
	
    @ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

    @ManyToOne
	@JoinColumn(name = "id_chat")
	private Chat chat;
	

}
