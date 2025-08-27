package com.fema.curricularizacao.DTO;

import com.fema.curricularizacao.enums.Atuacao;
import com.fema.curricularizacao.models.Funcionario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioFormDto {


    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 1)
    private String senha;

    @NotBlank
    private Atuacao atuacao;

    public Funcionario converterFormParaFuncionario(Funcionario funcionario) {
        funcionario.setNome(this.nome);
        funcionario.setEmail(this.email);
        funcionario.setSenha(this.senha);
        funcionario.setAtuacao(this.atuacao);
        return funcionario;
    }
}
