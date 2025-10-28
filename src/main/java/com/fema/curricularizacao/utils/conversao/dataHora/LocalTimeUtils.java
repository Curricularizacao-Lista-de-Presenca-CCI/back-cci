package com.fema.curricularizacao.utils.conversao.dataHora;


import com.fema.curricularizacao.utils.conversao.dataHora.formatacao.FormatacaoUtils;
import org.springframework.lang.NonNull;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public final class LocalTimeUtils {

    public static String converterLocalTimeAtualParaString() {
        return converterLocalTimeParaString(LocalTime.now());
    }

    public static String converterLocalTimeAtualComSegundosZeradosParaString() {
        return converterLocalTimeComSegundosZeradosParaString(LocalTime.now());
    }

    public static String converterHoraMinutoAtualParaString() {
        return converterHoraMinutoParaString(LocalTime.now());
    }

    public static String converterLocalTimeParaString(LocalTime hora) {
        return converterLocalTimeParaString(hora, FormatacaoUtils.FORMATADOR_HORA);
    }

    public static String converterLocalTimeComSegundosZeradosParaString(LocalTime hora) {
        return converterLocalTimeParaString(hora, FormatacaoUtils.FORMATADOR_HORA_MINUTO) + ":00";
    }

    public static String converterHoraMinutoParaString(LocalTime hora) {
        return converterLocalTimeParaString(hora, FormatacaoUtils.FORMATADOR_HORA_MINUTO);
    }

    public static String converterLocalTimeParaString(
            LocalTime hora, @NonNull DateTimeFormatter formatter) {
        if (Objects.isNull(hora)) throw new NullPointerException("A hora não pode ser nula!");

        try {
            return formatter.format(hora);
        } catch (DateTimeParseException e) {
            throw new DateTimeException("DateTimeFormatter inválido!");
        }
    }

    public static LocalTime converterStringParaLocalTime(String hora) {
        return converterStringParaLocalTime(hora, FormatacaoUtils.FORMATADOR_HORA);
    }

    public static LocalTime converterStringParaLocalTimeHoraMinuto(String hora) {
        return converterStringParaLocalTime(hora, FormatacaoUtils.FORMATADOR_HORA_MINUTO);
    }

    public static LocalTime converterStringParaLocalTime(
            String hora, @NonNull DateTimeFormatter formatter) {
        if (Objects.isNull(hora)) throw new NullPointerException("A hora não pode ser nula!");

        try {
            return LocalTime.parse(hora, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeException(
                    "Erro ao converter a hora para LocalTime. "
                            + "Formato da hora ou DateTimeFormatter inválido!");
        }
    }
}
