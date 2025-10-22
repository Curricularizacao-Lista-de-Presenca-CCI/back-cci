package com.fema.curricularizacao.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="evento_id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "data")
    private LocalDateTime data;

    @Column(name = "local")
    private String local;

    @Column(name = "uuid_arquivo")
    private UUID arquivo;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
}
