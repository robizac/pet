package br.com.fiap.pet.pet.controller.exception;

public class ControllerNotFoundException extends RuntimeException {

    public ControllerNotFoundException(String message) {
        super(message);
    }

    public ControllerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
