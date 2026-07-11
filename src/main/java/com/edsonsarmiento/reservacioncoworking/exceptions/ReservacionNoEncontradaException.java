package com.edsonsarmiento.reservacioncoworking.exceptions;

public class ReservacionNoEncontradaException extends RuntimeException {
    public ReservacionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
