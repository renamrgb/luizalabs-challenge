package com.renamrgb.luizalabschallenge.domain.communicationschedule;

import java.util.Optional;

public interface CommunicationSchedulerGateway {

    void save(CommunicationSchedule communicationSchedule);

    Optional<CommunicationSchedule> findById(Long id);
}
