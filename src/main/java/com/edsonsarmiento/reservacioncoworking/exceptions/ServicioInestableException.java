package com.edsonsarmiento.reservacioncoworking.exceptions;

import org.springframework.web.client.RestClientException;

public class ServicioInestableException extends RestClientException {
    public ServicioInestableException(String mensaje) {
        super(mensaje);
    }
}
