package com.renamrgb.luizalabschallenge.infra.rest.communicationschedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationSchedule;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleStatus;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.CommunicationScheduleType;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase.CancelCommunicationScheduleUseCase;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase.CreateCommunicationScheduleUseCase;
import com.renamrgb.luizalabschallenge.domain.communicationschedule.usecase.FindCommunicationScheduleByIdUseCase;
import com.renamrgb.luizalabschallenge.infra.rest.communicationschedule.dto.request.CreateCommunicationScheduleRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommunicationScheduleController.class)
class CommunicationScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCommunicationScheduleUseCase createCommunicationScheduleUseCase;
    @MockBean
    private FindCommunicationScheduleByIdUseCase findCommunicationScheduleByIdUseCase;
    @MockBean
    private CancelCommunicationScheduleUseCase cancelCommunicationScheduleUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void givenAValidRequest_whenCallCreate_thenReturnResponseStatusCreatedAndIdOfResgistre() throws Exception {
        ZonedDateTime date = ZonedDateTime.parse("2024-09-10T10:15:00Z");
        CreateCommunicationScheduleRequest request = new CreateCommunicationScheduleRequest(
            "destination",
            "message",
            "SMS",
            date
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
                date.equals(objc.getScheduleDate()) &&
                CommunicationScheduleType.SMS.equals(objc.getCommunicationType())
        ));
    }

    @Test
    void givenAValidId_whenCallFindById_thenReturnCommunicationSchedule() throws Exception {
        ZonedDateTime date = ZonedDateTime.parse("2024-09-10T10:15:00Z");
        long id = 1L;
        String destination = "destination";
        String message = "message";
        CommunicationScheduleType communicationType = CommunicationScheduleType.SMS;
        CommunicationScheduleStatus status = CommunicationScheduleStatus.PENDING;
        CommunicationSchedule communicationSchedule = CommunicationSchedule.of(
            id, destination, message,
            date, communicationType, status);
        when(findCommunicationScheduleByIdUseCase.execute(1L)).thenReturn(communicationSchedule);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
        String formattedDate = date.format(formatter);

        mockMvc.perform(get("/api/v1/communication/schedule/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.destination").value(destination))
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.communicationType").value(communicationType.name()))
            .andExpect(jsonPath("$.status").value(status.name()))
            .andExpect(jsonPath("$.scheduleDate").value(formattedDate));

        verify(findCommunicationScheduleByIdUseCase).execute(1L);
    }

    @Test
    void givenAValidId_whenCallCanceled_thenReturnNoContent() throws Exception {
        doNothing().when(cancelCommunicationScheduleUseCase).execute(1L);

        mockMvc.perform(put("/api/v1/communication/schedule/1/cancel")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(cancelCommunicationScheduleUseCase).execute(1L);
    }
}

