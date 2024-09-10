package com.renamrgb.luizalabschallenge.infra.persistence.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleStatus;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleType;
import com.renamrgb.luizalabschallenge.infra.persistence.communicationschedule.repository.CommunicationScheduleJPARepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;


@DataJpaTest
@ActiveProfiles("test")
class CommunicationScheduleJPAGatewayImplTest {

    @Autowired
    private CommunicationScheduleJPARepository communicationScheduleRepository;

    private CommunicationScheduleJPAGatewayImpl communicationScheduleJPAGateway;

    @BeforeEach
    void setUp() {
        communicationScheduleJPAGateway = new CommunicationScheduleJPAGatewayImpl(communicationScheduleRepository);
    }

    @Test
    void givenAnInvalidId_whenFindById_thenShouldReturnEmpty() {
        Optional<CommunicationSchedule> result = communicationScheduleJPAGateway.findById(999L);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void givenAValidCommunicationSchedule_whenSaveAndFindById_thenShouldFindTheSavedRecord() {
        String destination = "destination";
        String message = "message";
        CommunicationScheduleType communicationType = CommunicationScheduleType.SMS;
        CommunicationSchedule communicationSchedule = CommunicationSchedule.newInstance(
            destination,
            message,
            communicationType
        );

        communicationScheduleJPAGateway.save(communicationSchedule);
        Optional<CommunicationSchedule> result = communicationScheduleJPAGateway.findById(1L);

        Assertions.assertAll(
            () -> Assertions.assertEquals(1L, result.get().getId()),
            () -> Assertions.assertEquals(destination, result.get().getDestination()),
            () -> Assertions.assertEquals(message, result.get().getMessage()),
            () -> Assertions.assertEquals(communicationType, result.get().getCommunicationType()),
            () -> Assertions.assertEquals(CommunicationScheduleStatus.PENDING, result.get().getStatus())
        );
    }
}
