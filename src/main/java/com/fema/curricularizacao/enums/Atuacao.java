package com.fema.curricularizacao.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fema.curricularizacao.utils.conversao.enums.interfaces.GettersEnum;
import com.fema.curricularizacao.utils.conversao.enums.service.DesserializadorEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(using = DesserializadorEnum.class)
public enum Atuacao implements GettersEnum<String> {
    COORDENADOR("C", "Coordenador"),
    PROFESSOR("P", "Professor");

    private final String valor;
    private final String descricao;
}
