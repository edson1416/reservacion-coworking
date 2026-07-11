package com.edsonsarmiento.reservacioncoworking.exceptions;

import org.springframework.security.access.AccessDeniedException;

public class AccesoReservacionException extends AccessDeniedException {
    public AccesoReservacionException(String mensaje) {
        super(mensaje);
    }
}
