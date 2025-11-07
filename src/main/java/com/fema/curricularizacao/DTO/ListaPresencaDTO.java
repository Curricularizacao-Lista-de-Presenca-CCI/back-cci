package com.fema.curricularizacao.DTO;

import com.fema.curricularizacao.models.ListaPresenca;
import com.fema.curricularizacao.utils.conversao.dataHora.LocalDateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ListaPresencaDTO {

    public Long idAluno;

    public String nomeAluno;

    public String status;

    public String data;

    public ListaPresencaDTO(ListaPresenca listaPresenca, LocalDate data) {
        String nomeCompleto = listaPresenca.getId().getNomeAluno();
        String[] partes = nomeCompleto.split(" - ", 2);
        this.idAluno = Long.parseLong(partes[0].trim());
        this.nomeAluno = partes[1].trim();
        this.status = listaPresenca.getPresencaEnum().getDescricao();
        this.data = LocalDateUtils.converterLocalDateParaString(data);
    }

    public static List<ListaPresencaDTO> converter (List<ListaPresenca> listaPresenca, LocalDate data) {
        return listaPresenca.stream()
                .map(lista -> new ListaPresencaDTO(lista, data))
                .collect(Collectors.toList());
    }
}
