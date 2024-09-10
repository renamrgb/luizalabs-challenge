package com.renamrgb.luizalabschallenge.infra.persistence.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedulerGateway;
import com.renamrgb.luizalabschallenge.infra.persistence.communicationschedule.entity.CommunicationScheduleJPAEntity;
import com.renamrgb.luizalabschallenge.infra.persistence.communicationschedule.repository.CommunicationScheduleJPARepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Component
@Transactional(readOnly = true)
public class CommunicationScheduleJPAGatewayImpl implements CommunicationSchedulerGateway {

    private final CommunicationScheduleJPARepository communicationScheduleRepository;

    public CommunicationScheduleJPAGatewayImpl(final CommunicationScheduleJPARepository repository) {
        this.communicationScheduleRepository = Objects.requireNonNull(repository);
    }

    @Transactional
    @Override
    public void save(final CommunicationSchedule communicationSchedule) {
        communicationScheduleRepository.save(CommunicationScheduleJPAEntity.from(communicationSchedule));
    }

    @Override
    public Optional<CommunicationSchedule> findById(Long id) {
        return communicationScheduleRepository.findById(id)
            .map(CommunicationScheduleJPAEntity::toDomain);
    }
}
