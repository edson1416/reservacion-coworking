package com.edsonsarmiento.reservacioncoworking.entity;

import com.edsonsarmiento.reservacioncoworking.state.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reservaciones")
@Data
@ToString
@AllArgsConstructor
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idSala;
    private Long idUsuario;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private String estado;

    @Transient
    private ReservacionState estadoLogico;

    public Reservacion(){
        this.cambiarEstado(new EstadoPendiente());
    }

    public void confirmar(){
        estadoLogico.confirmar(this);
    }

    public void cancelar(){
        estadoLogico.cancelar(this);
    }

    public void completar(){
        estadoLogico.completar(this);
    }

    public void cambiarEstado(ReservacionState nuevoEstado) {
        this.estadoLogico = nuevoEstado;
        this.estado = nuevoEstado.getNombreEstado();
    }

    @PostLoad
    private void cargarEstadoLogico() {
        switch (this.estado) {
            case "PENDING" -> this.estadoLogico = new EstadoPendiente();
            case "CONFIRMED" -> this.estadoLogico = new EstadoConfirmado();
            case "CANCELLED" -> this.estadoLogico = new EstadoCancelado();
            case "COMPLETED" -> this.estadoLogico = new EstadoCompletado();
            default -> throw new IllegalArgumentException("Estado desconocido en BD: " + estado);
        }
    }
}
