package com.fema.curricularizacao.repositories;

import com.fema.curricularizacao.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
