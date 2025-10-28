package com.fema.curricularizacao.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaPresencaPK implements Serializable {

    @Column(name = "evento_id")
    private Long idEvento;

    @Column(name = "nome_aluno")
    private String nomeAluno;
}
