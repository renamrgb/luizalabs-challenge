package com.renamrgb.luizalabschallenge.infra.messaging.rabbitmq.emmiter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitMQEmitterTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitMQEmitter rabbitMQEmitter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(rabbitMQEmitter, "queueName", "testQueue");
    }

    @Test
    void givenAValidObject_whenCallSend_thenSendMessage() {
        Object message = new Object();
        rabbitMQEmitter.send(message);
        verify(rabbitTemplate).convertAndSend(eq("testQueue"), eq(message));
    }
}
