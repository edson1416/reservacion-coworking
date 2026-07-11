package com.edsonsarmiento.reservacioncoworking.exceptions;

public class EmailExisteException extends RuntimeException {

    public EmailExisteException(String message) {
        super(message);
    }

}
