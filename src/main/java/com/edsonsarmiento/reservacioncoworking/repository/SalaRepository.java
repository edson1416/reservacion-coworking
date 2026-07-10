package com.edsonsarmiento.reservacioncoworking.repository;

import com.edsonsarmiento.reservacioncoworking.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
}
