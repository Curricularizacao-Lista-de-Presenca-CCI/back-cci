package com.fema.curricularizacao.DTO;

import com.fema.curricularizacao.enums.Atuacao;
import com.fema.curricularizacao.models.Funcionario;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class InformacoesDosFuncionariosDTO{
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Atuacao atuacao;
    private boolean ativo;

    public InformacoesDosFuncionariosDTO(Funcionario funcionario){
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.email = funcionario.getEmail();
        this.senha = funcionario.getSenha();
        this.atuacao = funcionario.getAtuacao();
        this.ativo = funcionario.getAtivo();
    }

    public static List<InformacoesDosFuncionariosDTO> converter (List<Funcionario> funcionarios){
        return funcionarios.stream()
                .map(InformacoesDosFuncionariosDTO::new)
                .collect(Collectors.toList());
    }

}
