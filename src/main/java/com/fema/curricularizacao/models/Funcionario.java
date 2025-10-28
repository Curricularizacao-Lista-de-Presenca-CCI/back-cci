package com.fema.curricularizacao.models;

import com.fema.curricularizacao.enums.Atuacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="funcionario_id")
    private Long id;

    private String nome;

    @Column(name="email_usuario")
    private String email;

    private String senha;

    @Column(name="atuacao_enum")
    private Atuacao atuacao;

    public  Funcionario(Long id, String nome, String email, String senha, Atuacao atuacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.atuacao = atuacao;
    }
}
