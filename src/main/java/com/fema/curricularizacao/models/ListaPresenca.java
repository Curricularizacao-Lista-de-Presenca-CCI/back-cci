package com.fema.curricularizacao.models;


import com.fema.curricularizacao.enums.Presenca;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="lista_de_presenca")
public class ListaPresenca {

    @EmbeddedId
    private ListaPresencaPK id;

    @Column(name = "presenca_enum")
    private Presenca presencaEnum;
}
