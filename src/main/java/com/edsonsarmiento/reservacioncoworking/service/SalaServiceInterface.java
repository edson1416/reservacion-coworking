package com.edsonsarmiento.reservacioncoworking.service;

import com.edsonsarmiento.reservacioncoworking.dto.SalaDto;

import java.util.List;

public interface SalaServiceInterface {
    List<SalaDto> listarSalas();
    SalaDto buscarSalaPorId(Long id);
    SalaDto registrarSala(SalaDto salaDto);
    SalaDto editarrSala(SalaDto salaDto, Long id);
    void borrarSala(Long id);
}
