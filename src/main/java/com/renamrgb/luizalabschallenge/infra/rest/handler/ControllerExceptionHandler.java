package com.renamrgb.luizalabschallenge.infra.rest.handler;

import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessNotfoundException;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessUnexpectedException;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessValidationException;
import com.renamrgb.luizalabschallenge.infra.rest.handler.dto.ErrorResponse;
import com.renamrgb.luizalabschallenge.infra.rest.handler.dto.validation.ValidationItemResponse;
import com.renamrgb.luizalabschallenge.infra.rest.handler.dto.validation.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static com.renamrgb.luizalabschallenge.application.constants.MessageConstants.UNEXPECTED_ERROR_MESSAGE;

@ControllerAdvice
public class ControllerExceptionHandler {


    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(BusinessValidationException.class)
    public ResponseEntity<?> businessValidationException(BusinessValidationException e) {
        List<ValidationItemResponse> erros = e.getErrors().stream()
            .map(error -> new ValidationItemResponse(error.field(), error.message()))
            .toList();

        return ResponseEntity.unprocessableEntity().body(new ValidationResponse("Validation error", erros));
    }

    @ExceptionHandler(BusinessNotfoundException.class)
    public ResponseEntity<?> businessNotfoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BusinessUnexpectedException.class)
    public ResponseEntity<?> businessUnexpectedException(BusinessUnexpectedException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        final String errorMessage = String.format("The parameter '%s' must be of type '%s'",
            ex.getName(), ex.getRequiredType().getSimpleName());
        return ResponseEntity.internalServerError().body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception ex) {
        LOG.error("CONTROLLER_HANDLER - Unexpected error cause:[{}] error:[{}]", ex.getCause(), ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE));
    }
}
