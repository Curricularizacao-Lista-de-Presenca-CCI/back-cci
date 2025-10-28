package com.fema.curricularizacao.utils.conversao.enums.interfaces;


import com.fasterxml.jackson.annotation.JsonValue;

public interface ValorEnum<T> {
    @JsonValue
    T getValor();
}

