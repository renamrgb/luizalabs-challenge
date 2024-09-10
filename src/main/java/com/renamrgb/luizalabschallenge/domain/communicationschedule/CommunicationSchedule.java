package com.renamrgb.luizalabschallenge.domain.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessValidationException;
import com.renamrgb.luizalabschallenge.domain.exceptions.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class CommunicationSchedule {

    private Long id;
    private String destination;
    private String message;
    private CommunicationScheduleType communicationType;
    private CommunicationScheduleStatus status;

    private CommunicationSchedule(final Long id,
                                  final String destination,
                                  final String message,
                                  final CommunicationScheduleType communicationType,
                                  final CommunicationScheduleStatus status) {
        this.id = id;
        this.destination = destination;
        this.message = message;
        this.communicationType = communicationType;
        this.status = status;
        validate();
    }

    public static CommunicationSchedule newInstance(
        final String destination,
        final String message,
        final CommunicationScheduleType communicationType
    ) {
        return new CommunicationSchedule(null,
            destination,
            message,
            communicationType,
            CommunicationScheduleStatus.PENDING);
    }

    public static CommunicationSchedule of(
        final Long id,
        final String destination,
        final String message,
        final CommunicationScheduleType communicationType,
        final CommunicationScheduleStatus status
    ) {
        return new CommunicationSchedule(id, destination, message, communicationType, status);
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
        if (!errors.isEmpty()) {
            throw BusinessValidationException.with(errors);
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
            final int messageLenght = this.message.trim().length();
            if (messageLenght < 5 || messageLenght > 5000) {
                errors.add(new ErrorMessage("message", "Message must be between 5 and 5000"));
            }
        }
    }
}
