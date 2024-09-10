package com.renamrgb.luizalabschallenge.application.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedulerGateway;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessNotfoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FindCommunicationScheduleByIdUseCaseImplTest {

    @Mock
    private CommunicationSchedulerGateway communicationSchedulerGateway;

    @InjectMocks
    private FindCommunicationScheduleByIdUseCaseImpl useCase;


    @Test
    void givenAExistentId_whenCallExecute_thenReturnCommunicationSchedule() {
        CommunicationSchedule communicationSchedule = Mockito.mock(CommunicationSchedule.class);

        Mockito.when(communicationSchedulerGateway.findById(1L)).thenReturn(Optional.of(communicationSchedule));

        CommunicationSchedule response = useCase.execute(1L);

        Assertions.assertSame(communicationSchedule, response);
        Mockito.verify(communicationSchedulerGateway).findById(1L);
    }


    @Test
    void givenANonExistentId_whenCallExecute_thenThrowsBusinessNotfoundException() {
        Mockito.when(communicationSchedulerGateway.findById(1L)).thenReturn(Optional.empty());

        BusinessNotfoundException result = Assertions.assertThrows(BusinessNotfoundException.class, () -> useCase.execute(1L));

        Assertions.assertEquals("Communication with id '1' notfound", result.getMessage());
        Mockito.verify(communicationSchedulerGateway).findById(1L);
    }
}
