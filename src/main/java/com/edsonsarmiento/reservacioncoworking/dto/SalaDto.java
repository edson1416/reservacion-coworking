package com.edsonsarmiento.reservacioncoworking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SalaDto {
    private Long id;

    @NotNull(message = "El campo nombre es requerido")
    @NotBlank(message = "El campo nombre no debe estar vacío")
    private String nombre;

    @NotNull(message = "El campo tipo es requerido")
    @NotBlank(message = "El campo tipo no debe estar vacío")
    private String tipo;

    @NotNull(message = "El campo tipo es requerido")
    @Min(value = 1,message = "El valor de la capacidad debe ser mayor que 1")
    @Digits(integer = 9, fraction = 0, message = "Solo se permiten números enteros")
    private int capacidad;

    @NotNull(message = "El campo ubicacion es requerido")
    @NotBlank(message = "El campo ubicacion no debe estar vacío")
    private String ubicacion;

    @NotNull(message = "El campo tarifa es requerido")
    @PositiveOrZero
    @Digits(integer = 9, fraction = 2)
    private double tarifa;
}
