package com.edsonsarmiento.reservacioncoworking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OcupacionSalaDto {
    private Long id;
    private String nombreSala;
    private Double porcentajeOcupacion;
    private Long cantidadHorasReservadas;
}
