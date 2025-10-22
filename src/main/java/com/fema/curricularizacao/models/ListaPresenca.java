package com.fema.curricularizacao.models;

import com.fema.curricularizacao.enums.Presenca;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lista_de_presenca")
public class ListaPresenca {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_aluno")
    private String nomeAluno;

    @Enumerated(EnumType.STRING)
    @Column(name = "presenca_enum")
    private Presenca presenca;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;
}
