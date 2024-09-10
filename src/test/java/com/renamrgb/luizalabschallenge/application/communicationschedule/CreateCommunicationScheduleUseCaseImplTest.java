package com.renamrgb.luizalabschallenge.application.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleType;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedulerGateway;
import com.renamrgb.luizalabschallenge.domain.event.EventEmitter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
        CommunicationSchedule communicationSchedule = Mockito.mock(CommunicationSchedule.class);
        CommunicationSchedule savedCommunicationSchedule = Mockito.mock(CommunicationSchedule.class);

        Mockito.when(communicationSchedulerGateway.save(communicationSchedule)).thenReturn(savedCommunicationSchedule);

        useCase.execute(communicationSchedule);

        Mockito.verify(communicationSchedulerGateway).save(communicationSchedule);
        Mockito.verify(eventEmitter).send(savedCommunicationSchedule);
    }

}
