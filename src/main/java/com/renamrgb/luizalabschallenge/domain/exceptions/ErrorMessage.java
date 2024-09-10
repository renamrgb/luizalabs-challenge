package com.renamrgb.luizalabschallenge.domain.exceptions;

public record ErrorMessage(
    String field,
    String message
) {
}
