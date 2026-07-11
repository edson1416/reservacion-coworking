package com.edsonsarmiento.reservacioncoworking.service;

import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;
import com.edsonsarmiento.reservacioncoworking.exceptions.ServicioInestableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ValidacionPagoService {

    private final RestTemplate restTemplate;
    private final String MOCK_PAYMENT_URL = "http://localhost:8081/api/pagos/validar/";

    public ValidacionPagoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validarPago(Reservacion reservacion) {

        log.info("Iniciando validación de pago en servicio externo para reservacion {}", reservacion.getId());

        try{

            ResponseEntity<String> response = restTemplate.getForEntity(MOCK_PAYMENT_URL + reservacion.getId(), String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("El método de pago fue rechazado por el banco");
            }

            reservacion.completar();
            log.info("Validación de pago exitosa");

        }catch (RestClientException exception){
            // Si el servicio externo está apagado (inestable) o tarda más de 2 segundos (lento),
            log.error("Error al contactar al servicio de pagos: {}", exception.getMessage());

            throw new ServicioInestableException("El servicio de pagos no está disponible o está tardando demasiado. Intente confirmar más tarde.");
        }
    }

}
