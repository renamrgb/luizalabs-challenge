package com.renamrgb.luizalabschallenge.application.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedulerGateway;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase.CreateCommunicationScheduleUseCase;
import com.renamrgb.luizalabschallenge.domain.event.EventEmitter;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessUnexpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.renamrgb.luizalabschallenge.application.constants.MessageConstants.UNEXPECTED_ERROR_MESSAGE;

@Component
public class CreateCommunicationScheduleUseCaseImpl implements CreateCommunicationScheduleUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(CreateCommunicationScheduleUseCaseImpl.class);

    private final CommunicationSchedulerGateway communicationSchedulerGateway;
    private final EventEmitter eventEmitter;

    public CreateCommunicationScheduleUseCaseImpl(final CommunicationSchedulerGateway communicationSchedulerGateway, EventEmitter eventEmitter) {
        this.communicationSchedulerGateway = Objects.requireNonNull(communicationSchedulerGateway);
        this.eventEmitter = eventEmitter;
    }

    @Transactional
    @Override
    public CommunicationSchedule execute(final CommunicationSchedule communicationSchedule) {
        CommunicationSchedule savedCommunicationScheduler;
        try {
            savedCommunicationScheduler = communicationSchedulerGateway.save(communicationSchedule);
            eventEmitter.send(savedCommunicationScheduler);
        } catch (Throwable ex) {
            LOG.error("CREATE_COMMUNICATION - Failed to create schedule cause:[{}] error:[{}]", ex.getCause(), ex.getMessage());
            throw BusinessUnexpectedException.with(UNEXPECTED_ERROR_MESSAGE);
        }
        return savedCommunicationScheduler;
    }
}
