package com.edsonsarmiento.reservacioncoworking.dto;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class NuevaReservacionDto {

    @NotNull(message = "El campo id sala es requerido")
    private Long idSala;

    @NotNull(message = "La hora de entrada es requerida")
    @Future(message = "La hora de entrada debe ser mayor a la fecha y hora actual")
    private LocalDateTime horaEntrada;

    @NotNull(message = "La hora de salida es requerida")
    @Future(message = "La hora de salida debe ser mayor a la fecha y hora actual")
    private LocalDateTime horaSalida;

    @AssertTrue(message = "La hora de salida debe ser posterior a la hora de entrada")
    private boolean isRangoFechasValido() {
        if (horaEntrada == null || horaSalida == null) {
            return true;
        }
        return horaSalida.isAfter(horaEntrada);
    }
}
