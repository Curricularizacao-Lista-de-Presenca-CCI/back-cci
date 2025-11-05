package com.fema.curricularizacao.DTO;

import com.fema.curricularizacao.enums.Atuacao;
import lombok.Getter;

@Getter
public class AlterarFuncionarioDTO {

    public Boolean statusServidor;

    private String nome;

    private String email;

    private Atuacao atuacao;
}
