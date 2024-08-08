package com.ittiva.chat.exception;

public class RegistroInexistenteException extends Exception{
	
	private static final long serialVersionUID = 1l;


	/**
     * Constructor
     * @param message Mensaje de error
     */
    public RegistroInexistenteException(String message) {
        super(message);
    }

}
