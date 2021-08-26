package {PACKAGE}.exception;

import {PACKAGE}.dto.ValidateError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static {PACKAGE}.log.GeneralLogger.logRequest;
import static {PACKAGE}.log.GeneralLogger.logResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidateError> handleValidationExceptions(MethodArgumentNotValidException exception) {
        logRequest();

        List<ValidateError> validateErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new ValidateError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        logResponse(HttpStatus.BAD_REQUEST, validateErrors);
        return validateErrors;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<ValidateError> handleNotFoundException(NotFoundException e) {
        logRequest();
        logResponse(HttpStatus.NOT_FOUND, e.getErrorMessages());
        return e.getErrorMessages();
    }
}
