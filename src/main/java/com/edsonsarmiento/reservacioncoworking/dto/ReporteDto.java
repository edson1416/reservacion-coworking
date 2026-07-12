package com.edsonsarmiento.reservacioncoworking.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReporteDto {

    @NotNull(message = "La fechaInicio de entrada es requerida")
    private LocalDate fechaInicio;

    @NotNull(message = "La fechaFin de entrada es requerida")
    private LocalDate fechaFin;

    @AssertTrue(message = "La fecha fin debe ser posterior a la fecha de inicio")
    private boolean isRangoFechasValido() {
        if (fechaInicio == null || fechaFin == null) {
            return true;
        }
        return fechaFin.isAfter(fechaInicio) || fechaFin.isEqual(fechaInicio);
    }

}
