package com.fema.curricularizacao.utils.exceptions.custom;

public class ObjetoNaoEncontradoException extends RuntimeException {
    public ObjetoNaoEncontradoException(String message) {
        super(message);
    }

    public ObjetoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
