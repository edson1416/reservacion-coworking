package com.edsonsarmiento.reservacioncoworking.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacionService {

    @Async
    public void envairCorreoConfirmacion(String correo, Long idReservacion) {
        try{
            log.info("Iniciando simulación de envío de correo a [{}] para la reservación #{}", correo, idReservacion);
            Thread.sleep(5000);
            log.info("Correo enviado exitosamente");
        }catch (InterruptedException exception){
            Thread.currentThread().interrupt();
            log.error("Error al enviar correo", exception);
        }
    }

}
