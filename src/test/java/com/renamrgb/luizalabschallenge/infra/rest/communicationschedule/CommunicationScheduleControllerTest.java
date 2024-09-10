package com.renamrgb.luizalabschallenge.infra.rest.communicationschedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleType;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase.CreateCommunicationScheduleUseCase;
import com.renamrgb.luizalabschallenge.infra.rest.communicationschedule.dto.request.CreateCommunicationScheduleRequest;
import com.renamrgb.luizalabschallenge.infra.rest.communicationschedule.dto.response.CreateCommunicationScheduleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommunicationScheduleController.class)
class CommunicationScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCommunicationScheduleUseCase createCommunicationScheduleUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenAvalidRequest_whenCallCreate_thenReturnResponseStatusCreatedAndIdOfResgistre() throws Exception {
        ZonedDateTime sate = ZonedDateTime.parse("2024-09-10T10:15:00Z");
        CreateCommunicationScheduleRequest request = new CreateCommunicationScheduleRequest(
            "destination",
            "message",
            "SMS",
            sate
        );

        CommunicationSchedule communicationSchedule = mock(CommunicationSchedule.class);

        when(communicationSchedule.getId()).thenReturn(1L);
        when(createCommunicationScheduleUseCase.execute(any(CommunicationSchedule.class)))
            .thenReturn(communicationSchedule);

        mockMvc.perform(post("/api/v1/communication/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L));

        verify(createCommunicationScheduleUseCase).execute(argThat(objc ->
            "destination".equals(objc.getDestination()) &&
                "message".equals(objc.getMessage()) &&
                sate.equals(objc.getScheduleDate()) &&
                CommunicationScheduleType.SMS.equals(objc.getCommunicationType())
        ));
    }
}

