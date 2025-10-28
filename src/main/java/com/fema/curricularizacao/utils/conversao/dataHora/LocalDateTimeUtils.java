package com.fema.curricularizacao.utils.conversao.dataHora;


import com.fema.curricularizacao.utils.conversao.dataHora.formatacao.FormatacaoUtils;
import org.springframework.lang.NonNull;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public final class LocalDateTimeUtils {

    public static String converterLocalDateTimeAtualParaString() {
        return converterLocalDateTimeParaString(LocalDateTime.now());
    }

    public static String converterLocalDateTimeAtualComSegundosZeradosParaString() {
        return converterLocalDateTimeComSegundosZeradosParaString(LocalDateTime.now());
    }

    public static String converterLocalDateTimeAtualHoraMinutoParaString() {
        return converterLocalDateTimeParaString(
                LocalDateTime.now(), FormatacaoUtils.FORMATADOR_DATA_HORA_MINUTO);
    }

    public static String converterLocalDateTimeParaString(LocalDateTime dataHora) {
        return converterLocalDateTimeParaString(dataHora, FormatacaoUtils.FORMATADOR_DATA_HORA);
    }

    public static String converterLocalDateTimeComSegundosZeradosParaString(LocalDateTime dataHora) {
        return converterLocalDateTimeParaString(dataHora, FormatacaoUtils.FORMATADOR_DATA_HORA_MINUTO)
                + ":00";
    }

    public static String converterLocalDateTimeHoraMinutoParaString(LocalDateTime dataHora) {
        return converterLocalDateTimeParaString(dataHora, FormatacaoUtils.FORMATADOR_DATA_HORA_MINUTO);
    }

    public static String converterLocalDateTimeParaString(
            LocalDateTime dataHora, @NonNull DateTimeFormatter formatter) {
        if (Objects.isNull(dataHora)) throw new NullPointerException("A dataHora não pode ser nula!");

        try {
            return formatter.format(dataHora);
        } catch (DateTimeException e) {
            throw new DateTimeException("DateTimeFormatter inválido!");
        }
    }

    public static LocalDateTime converterStringParaLocalDateTime(String dataHora) {
        return converterStringParaLocalDateTime(dataHora, FormatacaoUtils.FORMATADOR_DATA_HORA);
    }

    public static LocalDateTime converterStringParaLocalDateTimeHoraMinuto(String dataHoraMinuto) {
        return converterStringParaLocalDateTime(
                dataHoraMinuto, FormatacaoUtils.FORMATADOR_DATA_HORA_MINUTO);
    }

    public static LocalDateTime converterStringParaLocalDateTime(
            String dataHora, @NonNull DateTimeFormatter formatter) {
        if (Objects.isNull(dataHora)) throw new NullPointerException("A dataHora não pode ser nula!");

        try {
            return LocalDateTime.parse(dataHora, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeException(
                    "Erro ao converter a dataHora para LocalDateTime. "
                            + "Formato da dataHora ou DateTimeFormatter inválido!");
        }
    }
}

