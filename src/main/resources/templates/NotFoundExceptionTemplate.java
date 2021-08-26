package {PACKAGE}.exception;

import {PACKAGE}.dto.ValidateError;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NotFoundException extends RuntimeException {
    private final List<ValidateError> errorMessages = new ArrayList<>();

    public NotFoundException addNotFound(String field, String message) {
        errorMessages.add(new ValidateError()
                        .setField(field)
                        .setErrorMessage(message));
        return this;
    }
}
