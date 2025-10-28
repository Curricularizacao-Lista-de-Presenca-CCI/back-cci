package com.fema.curricularizacao.utils.exceptions.custom;

public class TokenInvalidaException extends RuntimeException {

    public TokenInvalidaException(String message) {
        super(message);
    }
    public TokenInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }
}
