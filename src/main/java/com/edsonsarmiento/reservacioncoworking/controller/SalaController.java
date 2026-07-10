package com.edsonsarmiento.reservacioncoworking.controller;

import com.edsonsarmiento.reservacioncoworking.service.SalaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sala")
public class SalaController {

    private SalaService salaService;

    @GetMapping
    public ResponseEntity<?> listarSalas(){
        try{
            return new ResponseEntity<>(salaService.listarSalas(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
