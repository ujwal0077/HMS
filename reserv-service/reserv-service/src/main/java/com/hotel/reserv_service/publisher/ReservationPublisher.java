package com.hotel.reserv_service.publisher;



import com.hotel.reserv_service.config.RabbitMQConfig;
import com.hotel.shared.events.ReservationEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ReservationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendReservationEvent(ReservationEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, event);
    }
}
