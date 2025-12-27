package hicham.microservices.messaging.controller;

import hicham.microservices.messaging.domain.User;
import hicham.microservices.messaging.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProducerController {

    private final ProducerService producerService;
    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/produce")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestBody User user) {
        logger.info("Received request to produce message for user: {}", user);
        Map<String, Object> response = new HashMap<>();

        try {
            logger.info("Publishing message to RabbitMQ...");
            producerService.sendMessage(user);
            logger.info("Message published successfully for user: {}", user);

            response.put("status", "sent");
            response.put("message", "User message sent successfully");
            response.put("user", user);
            return ResponseEntity.ok(response);

        } catch (AmqpException e) {
            logger.error("RabbitMQ error while sending message: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "RabbitMQ unavailable: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } catch (Exception e) {
            logger.error("Unexpected error while sending message: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "Internal error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}