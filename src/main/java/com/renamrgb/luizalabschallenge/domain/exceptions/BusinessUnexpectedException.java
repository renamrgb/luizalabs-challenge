package com.renamrgb.luizalabschallenge.domain.exceptions;

public class BusinessUnexpectedException extends RuntimeException {

    private BusinessUnexpectedException(String message) {
        super(message);
    }

    public static BusinessUnexpectedException with(String message) {
        return new BusinessUnexpectedException(message);
    }
}
