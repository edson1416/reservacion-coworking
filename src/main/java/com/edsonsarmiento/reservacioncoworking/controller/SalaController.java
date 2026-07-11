package com.edsonsarmiento.reservacioncoworking.controller;

import com.edsonsarmiento.reservacioncoworking.dto.SalaDto;
import com.edsonsarmiento.reservacioncoworking.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sala")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<SalaDto>> listarSalas(){
        return new ResponseEntity<>(salaService.listarSalas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SalaDto> buscarSalaPorId(@PathVariable Long id){
        return new ResponseEntity<>(salaService.buscarSalaPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaDto> registrarSala(@Valid @RequestBody SalaDto salaDto){
        return new ResponseEntity<>(salaService.registrarSala(salaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaDto> editarrSala(@Valid @RequestBody SalaDto salaDto, @PathVariable Long id){
        return new ResponseEntity<>(salaService.editarrSala(salaDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SalaDto> borrarSala(@PathVariable Long id){
        return new ResponseEntity<>(salaService.borrarSala(id),HttpStatus.OK);
    }
}
