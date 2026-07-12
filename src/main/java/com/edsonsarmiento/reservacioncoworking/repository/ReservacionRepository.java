package com.edsonsarmiento.reservacioncoworking.repository;

import com.edsonsarmiento.reservacioncoworking.entity.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Reservacion r " +
            "WHERE r.idSala = :idSala " +
            "AND r.estado IN :estados " +
            "AND r.horaSalida > :entrada " +
            "AND r.horaEntrada < :salida")
    boolean existeCruceDeHorarios(
            @Param("idSala") Long id,
            @Param("entrada")LocalDateTime entrada,
            @Param("salida") LocalDateTime salida,
            @Param("estados") List<String> estados);

    List<Reservacion> findAllByIdUsuarioOrderByHoraEntradaDesc( Long idUsuario);

    @Query("SELECT r FROM Reservacion r WHERE r.horaEntrada >= :inicio AND r.horaSalida <= :fin AND r.estado = 'COMPLETED'")
    List<Reservacion> reservacionPorRango(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
}
