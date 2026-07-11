package com.edsonsarmiento.reservacioncoworking.mapper;

import com.edsonsarmiento.reservacioncoworking.dto.SalaDto;
import com.edsonsarmiento.reservacioncoworking.entity.Sala;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface SalaMapper {

    SalaDto entityToDto(Sala sala);
    Sala dtoToEntity(SalaDto salaDto);
    List<SalaDto> listSalaToListSalaDto(List<Sala> salas);
}
