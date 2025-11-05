package com.fema.curricularizacao.repositories;

import com.fema.curricularizacao.models.ListaPresenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListaPresecaRepository extends JpaRepository<ListaPresenca, Long> {
    Optional<ListaPresenca> findById_IdEventoAndId_NomeAluno(Long idEvento, String nomeAluno);

    List<ListaPresenca> findById_IdEvento(Long idEvento);

    List<ListaPresenca> findAllById_IdEvento(Long idEvento);
}
