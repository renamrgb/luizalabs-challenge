package com.renamrgb.luizalabschallenge.infra.rest.communicationschedule.dto.request;

import java.time.ZonedDateTime;

public record CreateCommunicationScheduleRequest(
    String destination,
    String message,
    String communicationType,
    ZonedDateTime scheduleDate
) {
}
