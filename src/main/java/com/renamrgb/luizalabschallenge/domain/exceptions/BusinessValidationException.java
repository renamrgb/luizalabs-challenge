package com.renamrgb.luizalabschallenge.domain.exceptions;

import java.util.List;

public class BusinessValidationException extends RuntimeException {

    private final List<ErrorMessage> errors;

    private BusinessValidationException(List<ErrorMessage> errors) {
        this.errors = errors;
    }

    public static BusinessValidationException with(ErrorMessage message) {
        return new BusinessValidationException(List.of(message));
    }

    public static BusinessValidationException with(List<ErrorMessage> messages) {
        return new BusinessValidationException(messages);
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }
}
