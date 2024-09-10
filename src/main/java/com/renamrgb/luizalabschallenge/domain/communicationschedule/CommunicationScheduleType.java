package com.renamrgb.luizalabschallenge.domain.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.exceptions.BusinessValidationException;
import com.renamrgb.luizalabschallenge.domain.exceptions.ErrorMessage;

import java.util.Arrays;

public enum CommunicationScheduleType {
    EMAIL,
    SMS,
    PUSH,
    WHATSAPP;

    public static CommunicationScheduleType from(final String type) {
        return Arrays.stream(values())
            .filter(value -> value.name().equalsIgnoreCase(type))
            .findFirst()
            .orElseThrow(() -> BusinessValidationException.with(new ErrorMessage("type", buildErrorMessage(type))));
    }

    private static String buildErrorMessage(String type) {
        return String.format(
            "Invalid communication type '%s'. Accepted types are: %s.",
            type,
            Arrays.toString(values())
        );
    }
}
