package com.fema.curricularizacao.utils.exceptions.custom;

public class ArquivoInvalidoException extends RuntimeException {
    public ArquivoInvalidoException(String message) {
        super(message);
    }

    public ArquivoInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
