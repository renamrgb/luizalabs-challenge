package com.renamrgb.luizalabschallenge.infra.persistence.communicationschedule.entity;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleStatus;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "communication_scheduler")
public class CommunicationScheduleJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_communication_scheduler")
    @SequenceGenerator(name = "seq_communication_scheduler", sequenceName = "seq_communication_scheduler", allocationSize = 1)
    private Long id;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "message", columnDefinition = "VARCHAR(5000)")
    private String message;

    @Column(name = "communication_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommunicationScheduleType communicationType;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommunicationScheduleStatus status;

    public CommunicationScheduleJPAEntity() {
    }

    public static CommunicationScheduleJPAEntity from(final CommunicationSchedule communicationSchedule) {
        CommunicationScheduleJPAEntity entity = new CommunicationScheduleJPAEntity();
        entity.setId(communicationSchedule.getId());
        entity.setDestination(communicationSchedule.getDestination());
        entity.setMessage(communicationSchedule.getMessage());
        entity.setCommunicationType(communicationSchedule.getCommunicationType());
        entity.setStatus(communicationSchedule.getStatus());
        return entity;
    }

    public CommunicationSchedule toDomain() {
        return CommunicationSchedule.of(
            id,
            destination,
            message,
            communicationType,
            status
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommunicationScheduleType getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(CommunicationScheduleType communicationType) {
        this.communicationType = communicationType;
    }

    public CommunicationScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(CommunicationScheduleStatus status) {
        this.status = status;
    }
}
