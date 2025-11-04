package com.fema.curricularizacao.DTO;

import com.fema.curricularizacao.models.Funcionario;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FuncionariosAtivosDTO {

    private final Long idFuncionario;

    private final String nome;

    private FuncionariosAtivosDTO(Funcionario funcionario) {
        this.idFuncionario = funcionario.getId();
        this.nome = funcionario.getNome();
    }

    public static List<FuncionariosAtivosDTO> converter (List<Funcionario> listaFuncionarios){
        return listaFuncionarios.stream()
                .map(FuncionariosAtivosDTO::new)
                .collect(Collectors.toList());
    }
}
