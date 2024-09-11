package com.renamrgb.luizalabschallenge.application.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedulerGateway;
import com.renamrgb.luizalabschallenge.domain.event.EventEmitter;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessUnexpectedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CreateCommunicationScheduleUseCaseImplTest {

    @Mock
    private CommunicationSchedulerGateway communicationSchedulerGateway;
    @Mock
    private EventEmitter eventEmitter;

    @InjectMocks
    private CreateCommunicationScheduleUseCaseImpl useCase;

    @Test
    void givenValidCommand_whenCallExecute_thenSaveAndSend() {
        CommunicationSchedule communicationSchedule = mock(CommunicationSchedule.class);
        CommunicationSchedule savedCommunicationSchedule = mock(CommunicationSchedule.class);

        Mockito.when(communicationSchedulerGateway.save(communicationSchedule)).thenReturn(savedCommunicationSchedule);

        useCase.execute(communicationSchedule);

        Mockito.verify(communicationSchedulerGateway).save(communicationSchedule);
        Mockito.verify(eventEmitter).send(savedCommunicationSchedule);
    }

    @Test
    void givenGenericError_whenCallExecute_thenThrowBusinessUnexpectedException() {
        Mockito.when(communicationSchedulerGateway.save(any())).thenThrow(new RuntimeException());

        BusinessUnexpectedException result = Assertions.assertThrows(BusinessUnexpectedException.class, () -> useCase.execute(mock(CommunicationSchedule.class)));

        Assertions.assertEquals("We had an internal problem, please try again later", result.getMessage());

    }
}
