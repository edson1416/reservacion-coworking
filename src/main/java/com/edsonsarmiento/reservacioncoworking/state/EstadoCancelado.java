package com.edsonsarmiento.reservacioncoworking.state;

import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;

public class EstadoCancelado implements ReservacionState{
    @Override
    public void confirmar(Reservacion reservacion) { throw new IllegalStateException("No puedes confirmar una reservación cancelada."); }
    @Override
    public void cancelar(Reservacion reservacion) { throw new IllegalStateException("Ya está cancelada."); }
    @Override
    public void completar(Reservacion reservacion) { throw new IllegalStateException("No se puede completar una reservación ya cancelada."); }
    @Override
    public String getNombreEstado() { return "CANCELLED"; }
}
