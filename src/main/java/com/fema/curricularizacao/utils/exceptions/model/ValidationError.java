package com.fema.curricularizacao.utils.exceptions.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends ExcecaoPadronizada {

    private static final long serialVersionUID = 1L;

    @Getter
    private final List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(
            Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}