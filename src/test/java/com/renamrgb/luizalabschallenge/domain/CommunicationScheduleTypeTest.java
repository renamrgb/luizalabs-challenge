package com.renamrgb.luizalabschallenge.domain;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleType;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommunicationScheduleTypeTest {


    @Test
    void givenEmailValue_whenCallFrom_thenReturnEmailInstance() {
        CommunicationScheduleType result = CommunicationScheduleType.from("email");
        Assertions.assertEquals(CommunicationScheduleType.EMAIL, result);
        result = CommunicationScheduleType.from("EMAIL");
        Assertions.assertEquals(CommunicationScheduleType.EMAIL, result);
    }

    @Test
    void givenSmsValue_whenCallFrom_thenReturnSmsInstance() {
        CommunicationScheduleType result = CommunicationScheduleType.from("sms");
        Assertions.assertEquals(CommunicationScheduleType.SMS, result);
        result = CommunicationScheduleType.from("SMS");
        Assertions.assertEquals(CommunicationScheduleType.SMS, result);
    }

    @Test
    void givenPushValue_whenCallFrom_thenReturnPushInstance() {
        CommunicationScheduleType result = CommunicationScheduleType.from("push");
        Assertions.assertEquals(CommunicationScheduleType.PUSH, result);
        result = CommunicationScheduleType.from("PUSH");
        Assertions.assertEquals(CommunicationScheduleType.PUSH, result);
    }

    @Test
    void givenWhatsappValue_whenCallFrom_thenReturnWhatsappInstance() {
        CommunicationScheduleType result = CommunicationScheduleType.from("whatsapp");
        Assertions.assertEquals(CommunicationScheduleType.WHATSAPP, result);
        result = CommunicationScheduleType.from("WHATSAPP");
        Assertions.assertEquals(CommunicationScheduleType.WHATSAPP, result);
    }

    @Test
    void givenUnknownValue_whenCallFrom_thenThrowsBusinessValidationException() {
        BusinessValidationException result = Assertions.assertThrows(BusinessValidationException.class, () -> CommunicationScheduleType.from("invalidValue"));
        Assertions.assertEquals(1, result.getErrors().size());
        Assertions.assertEquals("type", result.getErrors().get(0).field());
        Assertions.assertEquals("Invalid communication type 'invalidValue'. Accepted types are: [EMAIL, SMS, PUSH, WHATSAPP].", result.getErrors().get(0).message());
    }
}
