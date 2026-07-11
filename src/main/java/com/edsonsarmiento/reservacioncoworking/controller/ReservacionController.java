package com.edsonsarmiento.reservacioncoworking.controller;

import com.edsonsarmiento.reservacioncoworking.dto.NuevaReservacionDto;
import com.edsonsarmiento.reservacioncoworking.dto.ReservacionDto;
import com.edsonsarmiento.reservacioncoworking.service.ReservacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservaciones")
public class ReservacionController {

    private final ReservacionService reservacionService;

    public ReservacionController(ReservacionService reservacionService) {
        this.reservacionService = reservacionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReservacionDto> crearReservacion(@Valid @RequestBody NuevaReservacionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservacionService.crearReservacion(dto));
    }

    @GetMapping("/mis-reservaciones")
    @PreAuthorize("hasRole('USER')")
    public List<ReservacionDto> obtenerReservacionesPorUsuario() {
        return reservacionService.obtenerReservacionesPorUsuario();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ReservacionDto> listadoReservaciones() {
        return reservacionService.listadoReservaciones();
    }

    @PostMapping("/confirmar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservacionDto> confirmarReservacion(@Valid @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservacionService.confirmarReservacion(id));
    }

    @PostMapping("/cancelar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ReservacionDto> cancelarReservacion(@Valid @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reservacionService.cancelarReservacion(id));
    }
}
