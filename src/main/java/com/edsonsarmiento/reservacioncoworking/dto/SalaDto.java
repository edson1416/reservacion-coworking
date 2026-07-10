package com.edsonsarmiento.reservacioncoworking.dto;

import lombok.Data;

@Data
public class SalaDto {
    private Long id;
    private String nombre;
    private String tipo;
    private int capacidad;
    private String ubicacion;
    private double tarifa;
}
