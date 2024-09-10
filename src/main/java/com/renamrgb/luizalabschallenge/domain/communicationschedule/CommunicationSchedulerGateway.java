package com.renamrgb.luizalabschallenge.domain.communicationschedule;

import java.util.Optional;

public interface CommunicationSchedulerGateway {

    CommunicationSchedule save(CommunicationSchedule communicationSchedule);

    Optional<CommunicationSchedule> findById(Long id);
}
