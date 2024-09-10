package com.renamrgb.luizalabschallenge.application.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedulerGateway;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase.CreateCommunicationScheduleUseCase;
import com.renamrgb.luizalabschallenge.domain.event.EventEmitter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateCommunicationScheduleUseCaseImpl implements CreateCommunicationScheduleUseCase {

    private final CommunicationSchedulerGateway communicationSchedulerGateway;
    private final EventEmitter eventEmitter;

    public CreateCommunicationScheduleUseCaseImpl(final CommunicationSchedulerGateway communicationSchedulerGateway, EventEmitter eventEmitter) {
        this.communicationSchedulerGateway = Objects.requireNonNull(communicationSchedulerGateway);
        this.eventEmitter = eventEmitter;
    }

    @Override
    public void execute(final CommunicationSchedule communicationSchedule) {
        CommunicationSchedule savedCommunicationScheduler = communicationSchedulerGateway.save(communicationSchedule);
        eventEmitter.send(savedCommunicationScheduler);
    }
}
