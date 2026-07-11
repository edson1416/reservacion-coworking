package com.edsonsarmiento.reservacioncoworking.service;


import com.edsonsarmiento.reservacioncoworking.dto.SalaDto;
import com.edsonsarmiento.reservacioncoworking.entity.Sala;
import com.edsonsarmiento.reservacioncoworking.exceptions.SalaNoEncontradaException;
import com.edsonsarmiento.reservacioncoworking.mapper.SalaMapper;
import com.edsonsarmiento.reservacioncoworking.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService implements SalaServiceInterface{

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    public SalaService(SalaRepository salaRepository, SalaMapper salaMapper) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
    }

    @Override
    public List<SalaDto> listarSalas() {
        return salaMapper.listSalaToListSalaDto(salaRepository.findAll());
    }

    @Override
    public SalaDto buscarSalaPorId(Long id) {
        Sala sala = buscarSala(id);
        return salaMapper.entityToDto(sala);
    }

    @Override
    public SalaDto registrarSala(SalaDto salaDto) {
        Sala sala = salaMapper.dtoToEntity(salaDto);
        return salaMapper.entityToDto(salaRepository.save(sala));
    }

    @Override
    public SalaDto editarrSala(SalaDto salaDto, Long id) {
        Sala sala = buscarSala(id);
        sala.setNombre(salaDto.getNombre());
        sala.setTipo(salaDto.getTipo());
        sala.setCapacidad(salaDto.getCapacidad());
        sala.setUbicacion(salaDto.getUbicacion());
        sala.setTarifa(salaDto.getTarifa());
        salaRepository.save(sala);

        return salaMapper.entityToDto(salaRepository.save(sala));
    }

    @Override
    public void borrarSala(Long id) {
        Sala sala = buscarSala(id);
        salaRepository.delete(sala);
    }

    public Sala buscarSala(Long id) {
        return salaRepository.findById(id).orElseThrow(()-> new SalaNoEncontradaException("No se encontro el sala"));
    }

}
