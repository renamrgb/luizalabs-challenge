package com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;

public interface FindCommunicationScheduleByIdUseCase {

    CommunicationSchedule execute(long id);
}
