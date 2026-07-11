package com.edsonsarmiento.reservacioncoworking.state;

import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;

public class EstadoConfirmado implements ReservacionState{
    @Override
    public void confirmar(Reservacion reservacion) {
        throw new IllegalStateException("La reservacion ya esta confirmada");
    }

    @Override
    public void cancelar(Reservacion reservacion) {
        reservacion.cambiarEstado(new EstadoCancelado());
    }

    @Override
    public void completar(Reservacion reservacion) {
        reservacion.cambiarEstado(new EstadoCompletado());
    }

    @Override
    public String getNombreEstado() {
        return "CONFIRMED";
    }
}
