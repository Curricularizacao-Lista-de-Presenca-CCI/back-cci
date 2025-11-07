package com.fema.curricularizacao.utils.exceptions.custom;

public class PersistenciaDeDados extends RuntimeException {
    public PersistenciaDeDados(String message) {
        super(message);
    }
    public PersistenciaDeDados(String message, Throwable cause) {
        super(message, cause);
    }
}
