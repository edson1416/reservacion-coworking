package com.edsonsarmiento.reservacioncoworking.mapper;

import com.edsonsarmiento.reservacioncoworking.dto.ReservacionDto;
import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ReservacionMapper {

    ReservacionDto entityToDto(Reservacion reservacion);
    Reservacion dtoToEntity(ReservacionDto dto);
    List<ReservacionDto> listReservacionToListReservacionDto(List<Reservacion> reservaciones);
}
