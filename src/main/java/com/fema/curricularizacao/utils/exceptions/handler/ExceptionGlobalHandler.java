package com.fema.curricularizacao.utils.exceptions.handler;

import com.fema.curricularizacao.utils.exceptions.custom.*;
import com.fema.curricularizacao.utils.exceptions.model.ExcecaoPadronizada;
import com.fema.curricularizacao.utils.exceptions.model.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.DateTimeException;
import java.util.Objects;

@ControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(EmailOuSenhaInvalidos.class)
    public ResponseEntity<ExcecaoPadronizada> emailOuSenhaInvalidos(
            EmailOuSenhaInvalidos exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.BAD_REQUEST, "Email ou senha inválidos.", exception, request);
    }

    @ExceptionHandler(PersistenciaDeDados.class)
    public ResponseEntity<ExcecaoPadronizada> persistenciaDeDados(
            PersistenciaDeDados exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.BAD_REQUEST, "Ainda há dados persistentes.", exception, request);
    }

    @ExceptionHandler(EventoFinalizado.class)
    public ResponseEntity<ExcecaoPadronizada> eventoFinalizado(
            EventoFinalizado exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.BAD_REQUEST, "Evento já finalizado.", exception, request);
    }

    @ExceptionHandler(ErroConversaoHTMLPDF.class)
    public ResponseEntity<ExcecaoPadronizada> erroConversaoHTMLPDF(
            ErroConversaoHTMLPDF exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.BAD_REQUEST, "Falha na conversão do HTML", exception, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExcecaoPadronizada> handleAuthenticationException(
            AuthenticationException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.UNAUTHORIZED, "Falha na Autenticação", exception, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExcecaoPadronizada> handleAccessDeniedException(
            AccessDeniedException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.FORBIDDEN, "Acesso Negado", exception, request);
    }

    @ExceptionHandler(TokenInvalidaException.class)
    public ResponseEntity<ExcecaoPadronizada> tokenInvalidaException(
            TokenInvalidaException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.UNAUTHORIZED, "Erro ao manusear a token.", exception, request);
    }

    @ExceptionHandler(ArquivoInvalidoException.class)
    public ResponseEntity<ExcecaoPadronizada> arquivoInvalidoException(
            ArquivoInvalidoException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.BAD_REQUEST, "Erro ao manusear os dados.", exception, request);
    }

    @ExceptionHandler(PersistenciaDadosException.class)
    public ResponseEntity<ExcecaoPadronizada> persistenciaDadosException(
            PersistenciaDadosException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.BAD_REQUEST, "Erro ao manusear entidade.", exception, request);
    }

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    public ResponseEntity<ExcecaoPadronizada> objetoNaoEncontradoException(
            ObjetoNaoEncontradoException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.NOT_FOUND, "Valor não encontrado.", exception, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExcecaoPadronizada> illegalArgumentException(
            IllegalArgumentException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.BAD_REQUEST, "Valor inválido!", exception, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExcecaoPadronizada> illegalStateException(
            IllegalStateException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.BAD_REQUEST, "Estado inválido!", exception, request);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ExcecaoPadronizada> numberFormatException(
            NumberFormatException exception, HttpServletRequest request) {
        return padronizarExcecao(
                HttpStatus.BAD_REQUEST, "Erro ao converter valor para número!", exception, request);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ExcecaoPadronizada> dateTimeException(
            DateTimeException exception, HttpServletRequest request) {
        return padronizarExcecao(
                HttpStatus.BAD_REQUEST, "Erro ao manipular data e/ou hora!", exception, request);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExcecaoPadronizada> nullPointerException(
            NullPointerException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.BAD_REQUEST, "Valor nulo!", exception, request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ExcecaoPadronizada> objectNotFoundException(
            ObjectNotFoundException exception, HttpServletRequest request) {
        return padronizarExcecao(HttpStatus.NOT_FOUND, "Objeto não encontrado!", exception, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ExcecaoPadronizada> emptyResultDataAccessException(
            EmptyResultDataAccessException exception, HttpServletRequest request) {
        return padronizarExcecao(
                HttpStatus.NOT_FOUND, "Nenhum resultado retornado!", exception, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExcecaoPadronizada> validationException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {

        ValidationError validationError =
                new ValidationError(
                        System.currentTimeMillis(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        "Erro de Validação de Campos!",
                        exception.getMessage(),
                        request.getRequestURI());

        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach(
                        fieldError ->
                                validationError.addError(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(validationError);
    }

    private ResponseEntity<ExcecaoPadronizada> padronizarExcecao(
            HttpStatus httpStatus,
            String mensagemGenericaErro,
            Exception exception,
            HttpServletRequest request) {

        if (Objects.isNull(exception.getMessage())) exception.printStackTrace();

        ExcecaoPadronizada excecaoPadronizada =
                ExcecaoPadronizada.builder()
                        .timestamp(System.currentTimeMillis())
                        .status(httpStatus.value())
                        .error(mensagemGenericaErro)
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build();

        return ResponseEntity.status(httpStatus).body(excecaoPadronizada);
    }
}

