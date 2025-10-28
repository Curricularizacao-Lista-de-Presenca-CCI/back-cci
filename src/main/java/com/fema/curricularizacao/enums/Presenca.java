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
public enum Presenca implements GettersEnum<String> {
    SIM("S", "Sim"),
    NAO("N", "Não");

    private final String valor;
    private final String descricao;
}
