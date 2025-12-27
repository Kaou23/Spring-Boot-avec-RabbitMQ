package hicham.microservices.messaging.service;

import hicham.microservices.messaging.config.RabbitMQConfig;
import hicham.microservices.messaging.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(User user) {
        logger.info("Sending message to exchange: {}, routingKey: {}",
                RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, user);
        logger.info("Message sent successfully");
    }
}