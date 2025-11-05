package com.fema.curricularizacao.repositories;

import com.fema.curricularizacao.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findByEmail(String email);

    List<Funcionario> findAllByAtivo(boolean ativo);

    Optional<Funcionario> findById(Long id);
}
