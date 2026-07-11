package com.edsonsarmiento.reservacioncoworking.state;

import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;

public class EstadoPendiente implements ReservacionState{
    @Override
    public void confirmar(Reservacion reservacion) {
        reservacion.cambiarEstado(new EstadoConfirmado());
    }

    @Override
    public void cancelar(Reservacion reservacion) {
        reservacion.cambiarEstado(new EstadoCancelado());
    }

    @Override
    public void completar(Reservacion reservacion) {
        throw new IllegalStateException("No se puede completar una reservación pendiente.");
    }

    @Override
    public String getNombreEstado() {
        return "PENDING";
    }
}
