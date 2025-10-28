package com.fema.curricularizacao.utils.conversao.dataHora.formatacao;

import java.text.Collator;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class FormatacaoUtils {

    public static final DateTimeFormatter FORMATADOR_DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter FORMATADOR_DATA_HORA_MINUTO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATADOR_MES_ANO = DateTimeFormatter.ofPattern("MM/yyyy");

    public static final DateTimeFormatter FORMATADOR_HORA = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final DateTimeFormatter FORMATADOR_HORA_MINUTO =
            DateTimeFormatter.ofPattern("HH:mm");

    public static final Locale LOCALE_BR = new Locale("pt", "BR");

    public static final Collator COLLATOR = Collator.getInstance(LOCALE_BR);
}
