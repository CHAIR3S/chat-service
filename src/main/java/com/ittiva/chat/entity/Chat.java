package com.ittiva.chat.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_chat")
	private Long idChat;
	
	private String nombre;
	

    @Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime fecha;


    @ManyToOne
    @JoinColumn(name = "id_usuarioA")
    private Usuario usuarioA;

    @ManyToOne
    @JoinColumn(name = "id_usuarioB")
    private Usuario usuarioB;
    

}
