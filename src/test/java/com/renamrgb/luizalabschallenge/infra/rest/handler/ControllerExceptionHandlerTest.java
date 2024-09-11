package com.renamrgb.luizalabschallenge.infra.rest.handler;

import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessUnexpectedException;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessValidationException;
import com.renamrgb.luizalabschallenge.domain.exceptions.ErrorMessage;
import com.renamrgb.luizalabschallenge.infra.rest.handler.dto.ErrorResponse;
import com.renamrgb.luizalabschallenge.infra.rest.handler.dto.validation.ValidationItemResponse;
import com.renamrgb.luizalabschallenge.infra.rest.handler.dto.validation.ValidationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;

import static com.renamrgb.luizalabschallenge.application.constants.MessageConstants.UNEXPECTED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler handler = new ControllerExceptionHandler();

    @Test
    void givenBusinessValidationException_whenHandle_thenReturnValidationResponse() {
        BusinessValidationException ex = BusinessValidationException.with(new ErrorMessage("field", "message"));

        ResponseEntity<?> response = handler.businessValidationException(ex);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(new ValidationResponse("Validation error",
            Collections.singletonList(new ValidationItemResponse("field", "message"))), response.getBody());
    }

    @Test
    void givenBusinessNotfoundException_whenHandle_thenReturnNotFound() {
        ResponseEntity<?> response = handler.businessNotfoundException();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void givenBusinessUnexpectedException_whenHandle_thenReturnErrorResponse() {
        BusinessUnexpectedException ex = BusinessUnexpectedException.with("Unexpected error");

        ResponseEntity<?> response = handler.businessUnexpectedException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(new ErrorResponse("Unexpected error"), response.getBody());
    }

    @Test
    void givenMethodArgumentTypeMismatchException_whenHandle_thenReturnErrorResponse() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("id");
        when(ex.getRequiredType()).thenReturn((Class) Long.class);

        ResponseEntity<?> response = handler.methodArgumentTypeMismatchException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(new ErrorResponse("The parameter 'id' must be of type 'Long'"), response.getBody());
    }

    @Test
    void givenException_whenHandle_thenReturnUnexpectedErrorResponse() {
        Exception ex = new RuntimeException("Unexpected error");

        ResponseEntity<?> response = handler.exception(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(new ErrorResponse(UNEXPECTED_ERROR_MESSAGE), response.getBody());
    }
}
