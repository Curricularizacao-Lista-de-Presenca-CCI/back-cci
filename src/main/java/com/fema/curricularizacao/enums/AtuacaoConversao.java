package com.fema.curricularizacao.enums;

import com.fema.curricularizacao.utils.conversao.enums.interfaces.ConvertEnum;
import com.fema.curricularizacao.utils.conversao.enums.service.ConvertEnumNonNull;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AtuacaoConversao implements AttributeConverter<Atuacao, String> {

    private final ConvertEnum<Atuacao, String> convertEnum = new ConvertEnumNonNull<>();

    @Override
    public String convertToDatabaseColumn(Atuacao tipoTabela) {
        return convertEnum.getRepresentacaoValorEnumParaBancoDeDados(tipoTabela);
    }

    @Override
    public Atuacao convertToEntityAttribute(String dbData) {
        return convertEnum.getEnum(Atuacao.values(), dbData);
    }
}
