package com.edsonsarmiento.reservacioncoworking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservacionDto {
    private Long id;
    private Long idSala;
    private Long idUsuario;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private String estado;
}
