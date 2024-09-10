package com.renamrgb.luizalabschallenge.infra.rest.communicationschedule.dto.response;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleStatus;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleType;

import java.time.ZonedDateTime;

public record FindCommunicationScheduleResponse(
    Long id,
    String destination,
    String message,
    CommunicationScheduleType communicationType,
    CommunicationScheduleStatus status,
    ZonedDateTime scheduleDate
) {
}
