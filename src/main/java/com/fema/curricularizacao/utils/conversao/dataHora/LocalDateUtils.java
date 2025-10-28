package com.fema.curricularizacao.utils.conversao.dataHora;


import com.fema.curricularizacao.utils.conversao.dataHora.formatacao.FormatacaoUtils;
import org.springframework.lang.NonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public final class LocalDateUtils {

    public static String converterLocalDateAtualParaString() {
        return converterLocalDateParaString(LocalDate.now(), FormatacaoUtils.FORMATADOR_DATA);
    }

    public static String converterMesAnoAtualParaString() {
        return converterLocalDateParaString(LocalDate.now(), FormatacaoUtils.FORMATADOR_MES_ANO);
    }

    public static String converterLocalDateParaString(LocalDate data) {
        return converterLocalDateParaString(data, FormatacaoUtils.FORMATADOR_DATA);
    }

    public static String converterMesAnoParaString(LocalDate data) {
        return converterLocalDateParaString(data, FormatacaoUtils.FORMATADOR_MES_ANO);
    }

    public static String converterLocalDateParaString(
            LocalDate data, @NonNull DateTimeFormatter formatter) {
        if (Objects.isNull(data)) throw new NullPointerException("A data não pode ser nula!");

        try {
            return formatter.format(data);
        } catch (DateTimeException e) {
            throw new DateTimeException("DateTimeFormatter inválido!");
        }
    }

    public static LocalDate converterStringParaLocalDate(String data) {
        return converterStringParaLocalDate(data, FormatacaoUtils.FORMATADOR_DATA);
    }

    public static LocalDate converterStringParaLocalDate(
            String data, @NonNull DateTimeFormatter formatter) {
        if (Objects.isNull(data)) throw new NullPointerException("A data não pode ser nula!");

        try {
            return LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeException(
                    "Erro ao converter a data para LocalDate. "
                            + "Formato da data ou DateTimeFormatter inválido!");
        }
    }
}

