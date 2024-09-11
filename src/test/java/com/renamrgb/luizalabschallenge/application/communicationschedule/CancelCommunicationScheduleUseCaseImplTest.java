package com.renamrgb.luizalabschallenge.application.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleStatus;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleType;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedulerGateway;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessNotfoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelCommunicationScheduleUseCaseImplTest {


    @Mock
    private CommunicationSchedulerGateway communicationSchedulerGateway;


    @InjectMocks
    private CancelCommunicationScheduleUseCaseImpl useCase;

    @Test
    void givenAExistsId_whenCallExecute_thenUpdateToCanceled() {
        ZonedDateTime date = ZonedDateTime.parse("2024-09-10T10:15:00Z");

        CommunicationSchedule communicationSchedule = CommunicationSchedule.of(1L,
            "destination",
            "message",
            date,
            CommunicationScheduleType.SMS,
            CommunicationScheduleStatus.PENDING);

        when(communicationSchedulerGateway.findById(1L)).thenReturn(Optional.of(communicationSchedule));

        assertEquals(CommunicationScheduleStatus.PENDING, communicationSchedule.getStatus());

        useCase.execute(1L);

        verify(communicationSchedulerGateway).findById(1L);
        verify(communicationSchedulerGateway).save(argThat(objc ->
            "destination".equals(objc.getDestination()) &&
                "message".equals(objc.getMessage()) &&
                date.equals(objc.getScheduleDate()) &&
                CommunicationScheduleStatus.CANCELED.equals(objc.getStatus()) &&
                CommunicationScheduleType.SMS.equals(objc.getCommunicationType())));
    }

    @Test
    void givenANonExistentId_whenCallExecute_thenThrowsBusinessNotfoundException() {
        when(communicationSchedulerGateway.findById(1L)).thenReturn(Optional.empty());

        BusinessNotfoundException result = Assertions.assertThrows(BusinessNotfoundException.class, () -> useCase.execute(1L));

        assertEquals("Communication with id '1' notfound", result.getMessage());
        verify(communicationSchedulerGateway).findById(1L);
        verify(communicationSchedulerGateway, never()).save(any());

    }
}
