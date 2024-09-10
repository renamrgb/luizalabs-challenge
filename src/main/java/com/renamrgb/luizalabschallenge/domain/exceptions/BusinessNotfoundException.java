package com.renamrgb.luizalabschallenge.domain.exceptions;

public class BusinessNotfoundException extends RuntimeException {

    private BusinessNotfoundException(String message) {
        super(message);
    }

    public static BusinessNotfoundException with(String message) {
        return new BusinessNotfoundException(message);
    }
}
