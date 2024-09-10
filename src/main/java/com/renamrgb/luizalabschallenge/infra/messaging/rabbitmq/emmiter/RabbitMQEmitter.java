package com.renamrgb.luizalabschallenge.infra.messaging.rabbitmq.emmiter;

import com.renamrgb.luizalabschallenge.domain.event.EventEmitter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEmitter implements EventEmitter {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEmitter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(Object object) {
        rabbitTemplate.convertAndSend(queueName, object);
    }
}
