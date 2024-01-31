package es.nextdigital.demo.Controller.Exceptions;

public class NonAttachedCardException extends RuntimeException {
    public NonAttachedCardException(String message) {
        super(message);
    }
}
