package hicham.microservices.messaging.service;

import hicham.microservices.messaging.config.RabbitMQConfig;
import hicham.microservices.messaging.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(User user) {
        logger.info(">>> Received message from RabbitMQ: {}", user);
        logger.info(">>> User ID: {}, User Name: {}", user.getUserId(), user.getUserName());

        // Optionnel: Ici vous pouvez ajouter la persistance vers MySQL
        // userRepository.save(user);
    }
}
