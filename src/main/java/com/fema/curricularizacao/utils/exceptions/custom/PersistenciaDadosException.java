package com.fema.curricularizacao.utils.exceptions.custom;

public class PersistenciaDadosException extends RuntimeException {
    public PersistenciaDadosException(String message) {
        super(message);
    }
    public PersistenciaDadosException(String message, Throwable cause) {
        super(message, cause);
    }
}
