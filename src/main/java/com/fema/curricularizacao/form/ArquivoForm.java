package com.fema.curricularizacao.form;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArquivoForm {

    public int idFuncionario;

    @Size(min = 3)
    public String local;
}
