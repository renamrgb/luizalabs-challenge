package com.renamrgb.luizalabschallenge.infra.rest.handler.dto.validation;

import java.util.List;

public record ValidationResponse(
    String message,
    List<ValidationItemResponse> errors
) {
}
