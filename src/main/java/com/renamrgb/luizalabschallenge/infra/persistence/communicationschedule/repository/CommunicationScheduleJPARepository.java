package com.renamrgb.luizalabschallenge.infra.persistence.communicationschedule.repository;


import com.renamrgb.luizalabschallenge.infra.persistence.communicationschedule.entity.CommunicationScheduleJPAEntity;
import org.springframework.data.repository.CrudRepository;

public interface CommunicationScheduleJPARepository extends CrudRepository<CommunicationScheduleJPAEntity, Long> {
}
