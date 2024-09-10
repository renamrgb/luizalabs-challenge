package com.renamrgb.luizalabschallenge.infra.rest.communicationschedule;

import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleType;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase.CreateCommunicationScheduleUseCase;
import com.renamrgb.luizalabschallenge.infra.rest.communicationschedule.dto.request.CreateCommunicationScheduleRequest;
import com.renamrgb.luizalabschallenge.infra.rest.communicationschedule.dto.response.CreateCommunicationScheduleResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/communication/schedule",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class CommunicationScheduleController {

    private final CreateCommunicationScheduleUseCase createCommunicationScheduleUseCase;

    public CommunicationScheduleController(CreateCommunicationScheduleUseCase createCommunicationScheduleUseCase) {
        this.createCommunicationScheduleUseCase = createCommunicationScheduleUseCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateCommunicationScheduleRequest request) {
        CommunicationSchedule result = createCommunicationScheduleUseCase.execute(CommunicationSchedule.newInstance(
            request.destination(),
            request.message(),
            request.scheduleDate(),
            CommunicationScheduleType.from(request.communicationType())
        ));
        return ResponseEntity.status(201).body(new CreateCommunicationScheduleResponse(result.getId()));
    }
}
