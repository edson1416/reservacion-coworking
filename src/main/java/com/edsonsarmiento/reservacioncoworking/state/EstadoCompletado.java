package com.edsonsarmiento.reservacioncoworking.state;

import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;

public class EstadoCompletado implements ReservacionState{
    @Override
    public void confirmar(Reservacion reservacion) { throw new IllegalStateException("Reservación ya completada."); }
    @Override
    public void cancelar(Reservacion reservacion) { throw new IllegalStateException("No se puede cancelar algo completado."); }
    @Override
    public void completar(Reservacion reservacion) { throw new IllegalStateException("Ya está completada."); }
    @Override
    public String getNombreEstado() { return "COMPLETED"; }
}
