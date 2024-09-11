package com.renamrgb.luizalabschallenge.application.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedulerGateway;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase.FindCommunicationScheduleByIdUseCase;
import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessNotfoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.renamrgb.luizalabschallenge.application.constants.MessageConstants.NOT_FOUND_COMMUNICATION_MESSAGE;

@Component
public class FindCommunicationScheduleByIdUseCaseImpl implements FindCommunicationScheduleByIdUseCase {


    private final CommunicationSchedulerGateway communicationSchedulerGateway;

    public FindCommunicationScheduleByIdUseCaseImpl(final CommunicationSchedulerGateway communicationSchedulerGateway) {
        this.communicationSchedulerGateway = Objects.requireNonNull(communicationSchedulerGateway);
    }

    @Override
    public CommunicationSchedule execute(final long id) {
        return communicationSchedulerGateway.findById(id)
            .orElseThrow(() -> BusinessNotfoundException.with(NOT_FOUND_COMMUNICATION_MESSAGE.formatted(id)));
    }
}
