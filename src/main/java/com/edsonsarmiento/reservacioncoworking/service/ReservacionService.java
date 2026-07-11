package com.edsonsarmiento.reservacioncoworking.service;

import com.edsonsarmiento.reservacioncoworking.auth.entity.User;
import com.edsonsarmiento.reservacioncoworking.dto.NuevaReservacionDto;
import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;
import com.edsonsarmiento.reservacioncoworking.exceptions.ChoqueHorariosException;
import com.edsonsarmiento.reservacioncoworking.exceptions.SalaNoEncontradaException;
import com.edsonsarmiento.reservacioncoworking.repository.ReservacionRepository;
import com.edsonsarmiento.reservacioncoworking.repository.SalaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservacionService {

    private final ReservacionRepository reservacionRepository;
    private final SalaRepository salaRepository;

    public ReservacionService(ReservacionRepository reservacionRepository, SalaRepository salaRepository) {
        this.reservacionRepository = reservacionRepository;
        this.salaRepository = salaRepository;
    }

    @Transactional
    public void crearReservacion(NuevaReservacionDto dto) {

        Long idUsuario = obtenerIdUsuario();

        List<String> estadosOcupados = List.of("PENDING", "CONFIRMED");

        salaRepository.findById(dto.getIdSala()).orElseThrow(()-> new SalaNoEncontradaException("No se encontro el sala"));

        boolean hayChoque = reservacionRepository.existeCruceDeHorarios(dto.getIdSala(),dto.getHoraEntrada(),dto.getHoraSalida(),estadosOcupados);

        if (hayChoque) {
            throw new ChoqueHorariosException("La sala ya está reservada durante ese rango de horario");
        }

        Reservacion reservacion = new Reservacion();
        reservacion.setIdSala(dto.getIdSala());
        reservacion.setIdUsuario(idUsuario);
        reservacion.setHoraEntrada(dto.getHoraEntrada());
        reservacion.setHoraSalida(dto.getHoraSalida());

        reservacionRepository.save(reservacion);
    }

    private Long obtenerIdUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User usuarioAutenticado = (User) authentication.getPrincipal();
        return usuarioAutenticado.getId();
    }



}
