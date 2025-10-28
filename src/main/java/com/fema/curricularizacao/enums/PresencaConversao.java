package com.fema.curricularizacao.enums;

import com.fema.curricularizacao.utils.conversao.enums.interfaces.ConvertEnum;
import com.fema.curricularizacao.utils.conversao.enums.service.ConvertEnumNonNull;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PresencaConversao implements AttributeConverter<Presenca, String> {

    private final ConvertEnum<Presenca, String> convertEnum = new ConvertEnumNonNull<>();

    @Override
    public String convertToDatabaseColumn(Presenca situacao) {
        return convertEnum.getRepresentacaoValorEnumParaBancoDeDados(situacao);
    }

    @Override
    public Presenca convertToEntityAttribute(String dbData) {
        return convertEnum.getEnum(Presenca.values(), dbData);
    }
}