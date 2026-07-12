package com.edsonsarmiento.reservacioncoworking.service;

import com.edsonsarmiento.reservacioncoworking.auth.entity.User;
import com.edsonsarmiento.reservacioncoworking.dto.NuevaReservacionDto;
import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;
import com.edsonsarmiento.reservacioncoworking.entity.Sala;
import com.edsonsarmiento.reservacioncoworking.exceptions.ChoqueHorariosException;
import com.edsonsarmiento.reservacioncoworking.repository.ReservacionRepository;
import com.edsonsarmiento.reservacioncoworking.repository.SalaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReseravcionServiceTest {

    @Mock
    private ReservacionRepository reservacionRepository;

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private ReservacionService reservacionService;

    @Test
    void crearReservacion_LanzaException_CuandoHayChoqueDeHorarios(){

        //usuario falso
        User usuarioLogueado = new User();
        usuarioLogueado.setId(1L);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                usuarioLogueado, null, List.of() // Sin contraseña y sin roles especiales
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        NuevaReservacionDto dto = new NuevaReservacionDto();

        dto.setIdSala(1L);
        dto.setHoraEntrada(LocalDateTime.of(2026, 7, 12, 10, 0));
        dto.setHoraSalida(LocalDateTime.of(2026, 7, 12, 12, 0));

        Sala salaFalsa = new Sala();
        salaFalsa.setId(1L);

        // Simulamos que la sala existe
        when(salaRepository.findById(1L)).thenReturn(Optional.of(salaFalsa));

        // Simulamos que la base de datos dice "SÍ, hay un cruce de horarios"
        when(reservacionRepository.existeCruceDeHorarios(
                eq(1L), any(), any(), anyList()
        )).thenReturn(true);

        // 2 & 3. Ejecutar y Verificar (Act & Assert)
        assertThrows(ChoqueHorariosException.class, () -> {
            reservacionService.crearReservacion(dto);
        });

        // Verificamos que el save() NUNCA se haya llamado porque la excepción detuvo el flujo
        verify(reservacionRepository, never()).save(any(Reservacion.class));

    }

}
