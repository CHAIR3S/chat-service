package com.ittiva.chat.exception;

public class ContrasenaIncorrectaException extends RuntimeException{

    public ContrasenaIncorrectaException() {
        super("La contraseña es inválida.");
    }

}
