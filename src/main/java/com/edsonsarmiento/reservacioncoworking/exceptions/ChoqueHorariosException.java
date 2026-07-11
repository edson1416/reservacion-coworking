package com.edsonsarmiento.reservacioncoworking.exceptions;

public class ChoqueHorariosException extends IllegalStateException{
    public ChoqueHorariosException(String mensaje) {
        super(mensaje);
    }
}
