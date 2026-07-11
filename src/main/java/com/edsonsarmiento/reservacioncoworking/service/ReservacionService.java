package com.edsonsarmiento.reservacioncoworking.service;

import com.edsonsarmiento.reservacioncoworking.auth.entity.User;
import com.edsonsarmiento.reservacioncoworking.auth.repository.UserRepository;
import com.edsonsarmiento.reservacioncoworking.dto.NuevaReservacionDto;
import com.edsonsarmiento.reservacioncoworking.dto.ReservacionDto;
import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;
import com.edsonsarmiento.reservacioncoworking.exceptions.AccesoReservacionException;
import com.edsonsarmiento.reservacioncoworking.exceptions.ChoqueHorariosException;
import com.edsonsarmiento.reservacioncoworking.exceptions.ReservacionNoEncontradaException;
import com.edsonsarmiento.reservacioncoworking.exceptions.SalaNoEncontradaException;
import com.edsonsarmiento.reservacioncoworking.mapper.ReservacionMapper;
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
    private final ReservacionMapper reservacionMapper;
    private final UserRepository userRepository;
    private final NotificacionService notificacionService;

    public ReservacionService(ReservacionRepository reservacionRepository, SalaRepository salaRepository, ReservacionMapper reservacionMapper, NotificacionService notificacionService, UserRepository userRepository) {
        this.reservacionRepository = reservacionRepository;
        this.salaRepository = salaRepository;
        this.reservacionMapper = reservacionMapper;
        this.notificacionService = notificacionService;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReservacionDto crearReservacion(NuevaReservacionDto dto) {

        Long idUsuario = obtenerUsuario().getId();

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

        return reservacionMapper.entityToDto(reservacionRepository.save(reservacion));
    }

    public List<ReservacionDto> obtenerReservacionesPorUsuario() {
        Long idUsuario = obtenerUsuario().getId();
        List<Reservacion> lista = reservacionRepository.findAllByIdUsuarioOrderByHoraEntradaDesc(idUsuario);
        return reservacionMapper.listReservacionToListReservacionDto(lista);
    }

    public List<ReservacionDto> listadoReservaciones() {
        return reservacionMapper.listReservacionToListReservacionDto(reservacionRepository.findAll());
    }

    @Transactional
    public ReservacionDto confirmarReservacion(Long idReservacion) {
        Reservacion reservacion = buscarReservacion(idReservacion);
        User user = userRepository.findById(reservacion.getIdUsuario()).orElseThrow(()->new RuntimeException("No se encontro el usuario"));
        reservacion.confirmar();

        ReservacionDto reservacionDto = reservacionMapper.entityToDto(reservacionRepository.save(reservacion));

        notificacionService.envairCorreoConfirmacion(user.getEmail(), idReservacion);

        return reservacionDto;
    }

    @Transactional
    public ReservacionDto cancelarReservacion(Long idReservacion) {
        Reservacion reservacion = buscarReservacion(idReservacion);

        User usuarioAutenticado = obtenerUsuario();

        boolean esAdmin = usuarioAutenticado.getAuthorities().stream().anyMatch(rol-> rol.getAuthority().equals("ROLE_ADMIN"));

        boolean esMiReservacion = reservacion.getIdUsuario().equals(usuarioAutenticado.getId());

        if (!esAdmin && !esMiReservacion) {
            throw new AccesoReservacionException("No tienes permisos para cancelar esta reservación");
        }

        reservacion.cancelar();

        return reservacionMapper.entityToDto(reservacionRepository.save(reservacion));
    }

    private User obtenerUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    private Reservacion buscarReservacion(Long idReservacion) {
        return reservacionRepository.findById(idReservacion).orElseThrow(()-> new ReservacionNoEncontradaException("No se encontro la reservación"));
    }



}
