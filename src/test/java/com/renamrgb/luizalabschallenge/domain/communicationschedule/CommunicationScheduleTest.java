package com.renamrgb.luizalabschallenge.domain.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommunicationScheduleTest {


    @Test
    void givenAValidInput_whenCallNewInstance_thenReturnInstance() {
        String destination = "destination";
        String message = "message";
        CommunicationScheduleType type = CommunicationScheduleType.EMAIL;
        CommunicationSchedule result = CommunicationSchedule.newInstance(destination, message, type);

        assertAll(
            () -> assertNull(result.getId()),
            () -> assertEquals(destination, result.getDestination()),
            () -> assertEquals(message, result.getMessage()),
            () -> assertEquals(type, result.getCommunicationType()),
            () -> assertEquals(type, result.getCommunicationType()),
            () -> assertEquals(CommunicationScheduleStatus.PENDING, result.getStatus())
        );
    }

    @Test
    void givenAValidParameters_whenCallOf_thenReturnInstance() {
        Long id = 1L;
        String destination = "destination";
        String message = "message";
        CommunicationScheduleType type = CommunicationScheduleType.SMS;
        CommunicationScheduleStatus status = CommunicationScheduleStatus.PENDING;

        CommunicationSchedule result = CommunicationSchedule.of(id, destination, message, type, status);
        assertAll(
            () -> assertEquals(id, result.getId()),
            () -> assertEquals(destination, result.getDestination()),
            () -> assertEquals(message, result.getMessage()),
            () -> assertEquals(type, result.getCommunicationType()),
            () -> assertEquals(status, result.getStatus())
        );
    }

    @Test
    void givenAInvalidNullDestination_whenCallNewInstance_thenThrowsBusinessValidationException() {
        BusinessValidationException result = assertThrows(BusinessValidationException.class, () -> CommunicationSchedule.newInstance(null, "message", CommunicationScheduleType.SMS));

        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("destination", result.getErrors().get(0).field());
        Assertions.assertEquals("Destination is required", result.getErrors().get(0).message());
    }

    @Test
    void givenAInvalidEmptyDestination_whenCallNewInstance_thenThrowsBusinessValidationException() {
        BusinessValidationException result = assertThrows(BusinessValidationException.class, () -> CommunicationSchedule.newInstance("", "message", CommunicationScheduleType.SMS));

        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("destination", result.getErrors().get(0).field());
        Assertions.assertEquals("Destination is required", result.getErrors().get(0).message());
    }

    @Test
    void givenAInvalidBlankDestination_whenCallNewInstance_thenThrowsBusinessValidationException() {
        BusinessValidationException result = assertThrows(BusinessValidationException.class, () -> CommunicationSchedule.newInstance(" ", "message", CommunicationScheduleType.SMS));

        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("destination", result.getErrors().get(0).field());
        Assertions.assertEquals("Destination is required", result.getErrors().get(0).message());
    }

    @Test
    void givenAInvalidNullMessage_whenCallNewInstance_thenThrowsBusinessValidationException() {
        BusinessValidationException result = assertThrows(BusinessValidationException.class, () -> CommunicationSchedule.newInstance("destination", null, CommunicationScheduleType.SMS));

        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("message", result.getErrors().get(0).field());
        Assertions.assertEquals("Message is required", result.getErrors().get(0).message());
    }

    @Test
    void givenAInvalidEmptyMessage_whenCallNewInstance_thenThrowsBusinessValidationException() {
        BusinessValidationException result = assertThrows(BusinessValidationException.class, () -> CommunicationSchedule.newInstance("destination", "", CommunicationScheduleType.SMS));

        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("message", result.getErrors().get(0).field());
        Assertions.assertEquals("Message is required", result.getErrors().get(0).message());
    }

    @Test
    void givenAInvalidBlankMessage_whenCallNewInstance_thenThrowsBusinessValidationException() {
        BusinessValidationException result = assertThrows(BusinessValidationException.class, () -> CommunicationSchedule.newInstance("destination", " ", CommunicationScheduleType.SMS));

        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("message", result.getErrors().get(0).field());
        Assertions.assertEquals("Message is required", result.getErrors().get(0).message());
    }

    @Test
    void givenAInvalidMessageGreaterThan5000_whenCallNewInstance_thenThrowsBusinessValidationException() {
        String message = "a".repeat(5001);
        BusinessValidationException result = assertThrows(BusinessValidationException.class, () -> CommunicationSchedule.newInstance("destination", message, CommunicationScheduleType.SMS));

        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("message", result.getErrors().get(0).field());
        Assertions.assertEquals("Message must be between 5 and 5000", result.getErrors().get(0).message());
    }

    @Test
    void givenAInvalidMessageLessThan5_whenCallNewInstance_thenThrowsBusinessValidationException() {
        String message = "abcd ";
        BusinessValidationException result = assertThrows(BusinessValidationException.class, () -> CommunicationSchedule.newInstance("destination", message, CommunicationScheduleType.SMS));

        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("message", result.getErrors().get(0).field());
        Assertions.assertEquals("Message must be between 5 and 5000", result.getErrors().get(0).message());
    }

    @Test
    void givenAInvalidNullCommunicationType_whenCallNewInstance_thenThrowsBusinessValidationException() {
        BusinessValidationException result = assertThrows(BusinessValidationException.class, () -> CommunicationSchedule.newInstance("destination", "message", null));

        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("communicationType", result.getErrors().get(0).field());
        Assertions.assertEquals("CommunicationType is required", result.getErrors().get(0).message());
    }

}
