package com.edsonsarmiento.reservacioncoworking.service;


import com.edsonsarmiento.reservacioncoworking.dto.SalaDto;
import com.edsonsarmiento.reservacioncoworking.mapper.SalaMapper;
import com.edsonsarmiento.reservacioncoworking.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService implements SalaServiceInterface{

    private SalaRepository salaRepository;
    private SalaMapper salaMapper;

    @Override
    public List<SalaDto> listarSalas() {
        return salaMapper.listSalaToListSalaDto(salaRepository.findAll());
    }

    @Override
    public SalaDto buscarSalaPorId(Long id) {
        return null;
    }

    @Override
    public SalaDto registrarSala(SalaDto salaDto) {
        return null;
    }

    @Override
    public SalaDto editarrSala(SalaDto salaDto) {
        return null;
    }

    @Override
    public void borrarSala(Long id) {

    }
}
