package com.edsonsarmiento.reservacioncoworking.integration;


import com.edsonsarmiento.reservacioncoworking.auth.entity.User;
import com.edsonsarmiento.reservacioncoworking.dto.NuevaReservacionDto;
import com.edsonsarmiento.reservacioncoworking.dto.ReservacionDto;
import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;
import com.edsonsarmiento.reservacioncoworking.entity.Sala;
import com.edsonsarmiento.reservacioncoworking.exceptions.SalaNoEncontradaException;
import com.edsonsarmiento.reservacioncoworking.repository.ReservacionRepository;
import com.edsonsarmiento.reservacioncoworking.repository.SalaRepository;
import com.edsonsarmiento.reservacioncoworking.service.ReservacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReservacionIntegrationTest {

    @Autowired
    private ReservacionService reservacionService;

    @Autowired
    private ReservacionRepository reservacionRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Test
    void integrar_CrearReservacion_GuardarCorrectamenteEnDB(){

        // Preparo la BD en memoria insertando una sala real
        Sala sala = new Sala();
        sala.setNombre("Sala de Reuniones A");
        sala.setTarifa(15.00);
        salaRepository.save(sala);

        //usuario falso
        User usuarioLogueado = new User();
        usuarioLogueado.setId(1L);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                usuarioLogueado, null, List.of() // Sin contraseña y sin roles especiales
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Preparo el DTO
        NuevaReservacionDto dto = new NuevaReservacionDto();
        dto.setIdSala(sala.getId());
        dto.setHoraEntrada(LocalDateTime.of(2026, 7, 15, 14, 0));
        dto.setHoraSalida(LocalDateTime.of(2026, 7, 15, 16, 0)); // 2 horas

        ReservacionDto resultado = reservacionService.crearReservacion(dto);

        assertNotNull(resultado.getId());

        Reservacion reservacionGuardada = reservacionRepository.findById(resultado.getId()).orElse(null);

        assertNotNull(reservacionGuardada);
        assertEquals(sala.getId(), reservacionGuardada.getIdSala());
        assertEquals(30.00, reservacionGuardada.getTotalPagar()); // 15.00 * 2 horas
    }

    @Test
    void integrar_CrearReservacion_FallaPorRestriccionDeIntegridad(){

        //usuario falso
        User usuarioLogueado = new User();
        usuarioLogueado.setId(1L);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                usuarioLogueado, null, List.of() // Sin contraseña y sin roles especiales
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        //intento guardar con un ID de sala que no existe
        NuevaReservacionDto dto = new NuevaReservacionDto();
        dto.setIdSala(999L);
        dto.setHoraEntrada(LocalDateTime.now());
        dto.setHoraSalida(LocalDateTime.now().plusHours(1));

        assertThrows(SalaNoEncontradaException.class, () -> {
            reservacionService.crearReservacion(dto);
        });
    }
}
