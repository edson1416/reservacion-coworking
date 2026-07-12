package com.edsonsarmiento.reservacioncoworking.exceptions;

public class PagoRechazadoException extends RuntimeException {
    public PagoRechazadoException(String message) {
        super(message);
    }
}
