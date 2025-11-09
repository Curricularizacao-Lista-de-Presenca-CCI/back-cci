package com.fema.curricularizacao.DTO;

import com.fema.curricularizacao.models.Evento;
import com.fema.curricularizacao.utils.conversao.dataHora.LocalDateUtils;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BuscarEventosCadastradoDTO {

    public Long idEvento;

    public String titulo;

    public String dataEvento;

    public String local;

    public String funcionario;

    public Long idFuncionario;

    public Boolean finalizado;

    public BuscarEventosCadastradoDTO(Evento evento){
        this.idEvento = evento.getId();
        this.titulo = evento.getTitulo();
        this.dataEvento = LocalDateUtils.converterLocalDateParaString(evento.getData());
        this.finalizado = evento.getFinalizado();
        this.local = evento.getLocal();
        this.funcionario = evento.getFuncionario().getNome();
        this.idFuncionario = evento.getFuncionario().getId();
    }

    public static List<BuscarEventosCadastradoDTO> converter(List<Evento> eventos){
        return eventos.stream().map(BuscarEventosCadastradoDTO::new).collect(Collectors.toList());
    }
}
