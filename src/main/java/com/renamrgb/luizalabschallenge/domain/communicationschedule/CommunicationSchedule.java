package com.renamrgb.luizalabschallenge.domain.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessValidationException;
import com.renamrgb.luizalabschallenge.domain.exceptions.ErrorMessage;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommunicationSchedule implements Serializable {

    private Long id;
    private String destination;
    private String message;
    private CommunicationScheduleType communicationType;
    private CommunicationScheduleStatus status;
    private ZonedDateTime scheduleDate;

    private CommunicationSchedule(final Long id,
                                  final String destination,
                                  final String message,
                                  final ZonedDateTime scheduleDate,
                                  final CommunicationScheduleType communicationType,
                                  final CommunicationScheduleStatus status) {
        this.id = id;
        this.destination = destination;
        this.message = message;
        this.scheduleDate = scheduleDate;
        this.communicationType = communicationType;
        this.status = status;
        validate();
    }

    public static CommunicationSchedule newInstance(
        final String destination,
        final String message,
        final ZonedDateTime scheduleDate,
        final CommunicationScheduleType communicationType
    ) {
        return new CommunicationSchedule(null,
            destination,
            message,
            scheduleDate,
            communicationType,
            CommunicationScheduleStatus.PENDING);
    }

    public static CommunicationSchedule of(
        final Long id,
        final String destination,
        final String message,
        final ZonedDateTime scheduleDate,
        final CommunicationScheduleType communicationType,
        final CommunicationScheduleStatus status
    ) {
        return new CommunicationSchedule(id, destination, message, scheduleDate, communicationType, status);
    }

    public Long getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public String getMessage() {
        return message;
    }

    public CommunicationScheduleType getCommunicationType() {
        return communicationType;
    }

    public CommunicationScheduleStatus getStatus() {
        return status;
    }

    public ZonedDateTime getScheduleDate() {
        return scheduleDate;
    }

    /**
     * Valida a instância atual da classe Pensando que em um dominio rico a propria class deve saber se validar.
     *
     * <p>
     * TODO: A lógica de validação pode ser movida para uma classe separada responsável por validação,
     *       preservando o Princípio da Responsabilidade Única (S).
     * </p>
     */
    private void validate() {
        List<ErrorMessage> errors = new ArrayList<>();
        validationDestinationConstraints(errors);
        validateMessageConstraints(errors);
        validationCommunicationTypeConstraints(errors);
        validateScheduleDateConstraints(errors);
        if (!errors.isEmpty()) {
            throw BusinessValidationException.with(errors);
        }
    }

    private void validateScheduleDateConstraints(List<ErrorMessage> errors) {
        if(this.scheduleDate == null) {
            errors.add(new ErrorMessage("scheduleDate", "Scheduledate is required"));
        }
    }

    private void validationCommunicationTypeConstraints(List<ErrorMessage> errors) {
        if (this.communicationType == null) {
            errors.add(new ErrorMessage("communicationType", "CommunicationType is required"));
        }
    }

    private void validationDestinationConstraints(List<ErrorMessage> errors) {
        if (this.destination == null || this.destination.isBlank()) {
            errors.add(new ErrorMessage("destination", "Destination is required"));
        }
    }

    private void validateMessageConstraints(List<ErrorMessage> errors) {
        if (this.message == null || this.message.isBlank()) {
            errors.add(new ErrorMessage("message", "Message is required"));
        } else {
            final int messageLength = this.message.trim().length();
            if (messageLength < 5 || messageLength > 5000) {
                errors.add(new ErrorMessage("message", "Message must be between 5 and 5000"));
            }
        }
    }
}
