package com.edsonsarmiento.reservacioncoworking.controller;

import com.edsonsarmiento.reservacioncoworking.service.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
