package com.fema.curricularizacao.utils.exceptions.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class FieldMessage implements Serializable {

    @Getter(value = AccessLevel.NONE)
    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String message;
}

