package com.fema.curricularizacao.utils.exceptions.custom;

public class EmailOuSenhaInvalidos extends RuntimeException {
    public EmailOuSenhaInvalidos(String message) {
        super(message);
    }

    public EmailOuSenhaInvalidos(String message, Throwable cause) {
        super(message, cause);
    }
}
