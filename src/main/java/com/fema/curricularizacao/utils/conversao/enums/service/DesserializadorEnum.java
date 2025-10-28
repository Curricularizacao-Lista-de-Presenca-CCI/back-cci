package com.fema.curricularizacao.utils.conversao.enums.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fema.curricularizacao.utils.conversao.enums.interfaces.ValorEnum;

import java.io.IOException;
import java.util.Objects;

public class DesserializadorEnum<E extends Enum<E> & ValorEnum<?>> extends JsonDeserializer<E> implements ContextualDeserializer {

    private E[] constantesEnum;
    private Class<?> classeDoValorDasConstantes;


    @Override
    public E deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String nomeTipoEnum = constantesEnum.getClass().getSimpleName().split("\\[]")[0];
        Object valorEnum = jsonParser.readValueAs(Object.class);

        if (Objects.nonNull(valorEnum)) {
            if (!valorEnum.getClass().equals(classeDoValorDasConstantes))
                throw new IllegalArgumentException("Tipo de Objeto inválido para desserializar "
                        + "o Enum '" + nomeTipoEnum + "'! Tipo permitido: " + classeDoValorDasConstantes
                        + " -> Tipo recebido: " + valorEnum.getClass());

            for (ValorEnum<?> constanteEnum : constantesEnum)
                if (constanteEnum.getValor().equals(valorEnum))
                    return (E) constanteEnum;
        }
        else
            return null;

        throw new IllegalArgumentException("O campo do Enum '" + nomeTipoEnum
                + "' contém valor inválido!");
    }

    @Override
    public JsonDeserializer<E> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JsonMappingException {

        Class<E> tipoEnum = (Class<E>) ctxt.getContextualType().getRawClass();
        constantesEnum = tipoEnum.getEnumConstants();
        classeDoValorDasConstantes = constantesEnum[0].getValor().getClass();

        return this;
    }
}
