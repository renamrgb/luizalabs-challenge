package com.renamrgb.luizalabschallenge.infra.rest.handler.dto.validation;

public record ValidationItemResponse(
    String field,
    String error
) {
}
