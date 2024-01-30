package es.nextdigital.demo.Controller.Exceptions;

public class TarjetaNoAsociadaException extends RuntimeException {
    public TarjetaNoAsociadaException(String mensaje) {
        super(mensaje);
    }
}
