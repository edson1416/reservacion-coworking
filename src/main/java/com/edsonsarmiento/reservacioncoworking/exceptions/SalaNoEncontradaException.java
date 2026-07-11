package com.edsonsarmiento.reservacioncoworking.exceptions;

public class SalaNoEncontradaException extends RuntimeException {
    public SalaNoEncontradaException(String message) {
        super(message);
    }
}
