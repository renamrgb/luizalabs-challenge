package com.renamrgb.luizalabschallenge.application.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedulerGateway;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase.CancelCommunicationScheduleUseCase;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessNotfoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.renamrgb.luizalabschallenge.application.constants.MessageConstants.NOT_FOUND_COMMUNICATION_MESSAGE;

@Component
public class CancelCommunicationScheduleUseCaseImpl implements CancelCommunicationScheduleUseCase {

    private final CommunicationSchedulerGateway communicationSchedulerGateway;

    public CancelCommunicationScheduleUseCaseImpl(final CommunicationSchedulerGateway communicationSchedulerGateway) {
        this.communicationSchedulerGateway = Objects.requireNonNull(communicationSchedulerGateway);
    }

    @Override
    public void execute(long id) {
        communicationSchedulerGateway.findById(id)
            .ifPresentOrElse(
                communicationSchedule -> {
                    communicationSchedule.markAsCanceled();
                    communicationSchedulerGateway.save(communicationSchedule);
                },
                () -> {
                    throw BusinessNotfoundException.with(NOT_FOUND_COMMUNICATION_MESSAGE.formatted(id));
                }
            );
    }
}
