package com.edsonsarmiento.reservacioncoworking.state;

import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;

public interface ReservacionState {
    void confirmar(Reservacion reservacion);
    void cancelar(Reservacion reservacion);
    void completar(Reservacion reservacion);
    String getNombreEstado();
}
