package com.fema.curricularizacao.DTO;

import com.fema.curricularizacao.models.Evento;
import com.fema.curricularizacao.utils.conversao.dataHora.LocalDateUtils;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BuscarEventosCadastradoDTO {

    public Long idEvento;

    public String nomeEvento;

    public String dataEvento;

    public BuscarEventosCadastradoDTO(Evento evento){
        this.idEvento = evento.getId();
        this.nomeEvento = evento.getTitulo();
        this.dataEvento = LocalDateUtils.converterLocalDateParaString(evento.getData());
    }

    public static List<BuscarEventosCadastradoDTO> converter(List<Evento> eventos){
        return eventos.stream().map(BuscarEventosCadastradoDTO::new).collect(Collectors.toList());
    }
}
