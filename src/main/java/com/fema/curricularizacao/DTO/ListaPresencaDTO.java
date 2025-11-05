package com.fema.curricularizacao.DTO;

import com.fema.curricularizacao.models.ListaPresenca;

import java.util.List;
import java.util.stream.Collectors;

public class ListaPresencaDTO {

    public Long idAluno;

    public String nomeAluno;

    public String status;

    public ListaPresencaDTO(ListaPresenca listaPresenca) {
        this.idAluno = listaPresenca.getId().getIdEvento();
        this.nomeAluno = listaPresenca.getId().getNomeAluno();
        this.status = listaPresenca.getPresencaEnum().getDescricao();
    }

    public static List<ListaPresencaDTO> converter (List<ListaPresenca> listaPresenca) {
        return listaPresenca.stream()
                .map(ListaPresencaDTO::new)
                .collect(Collectors.toList());
    }
}
