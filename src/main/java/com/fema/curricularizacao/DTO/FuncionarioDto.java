package com.fema.curricularizacao.DTO;

import com.fema.curricularizacao.enums.Atuacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDto{
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Atuacao atuacao;
    private String token;

    public FuncionarioDto(Long id, String nome, String email, String senha, Atuacao atuacao, String token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.atuacao = atuacao;
        this.token = token;
    }
}
