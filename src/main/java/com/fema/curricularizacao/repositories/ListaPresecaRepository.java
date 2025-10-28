package com.fema.curricularizacao.repositories;

import com.fema.curricularizacao.models.ListaPresenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaPresecaRepository extends JpaRepository<ListaPresenca, Long> {
}
