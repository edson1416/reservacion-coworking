package com.edsonsarmiento.reservacioncoworking.controller;

import com.edsonsarmiento.reservacioncoworking.dto.SalaDto;
import com.edsonsarmiento.reservacioncoworking.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sala")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public ResponseEntity<?> listarSalas(){
        return new ResponseEntity<>(salaService.listarSalas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDto> buscarSalaPorId(@PathVariable Long id){
        return new ResponseEntity<>(salaService.buscarSalaPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SalaDto> registrarSala(@Valid @RequestBody SalaDto salaDto){
        return new ResponseEntity<>(salaService.registrarSala(salaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDto> editarrSala(@Valid @RequestBody SalaDto salaDto, @PathVariable Long id){
        return new ResponseEntity<>(salaService.editarrSala(salaDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarSala(@PathVariable Long id){
        salaService.borrarSala(id);
        return new ResponseEntity<>("Sala eliminada correctamente",HttpStatus.OK);
    }
}
