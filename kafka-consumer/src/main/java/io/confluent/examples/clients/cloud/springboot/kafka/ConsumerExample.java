package io.confluent.examples.clients.cloud.springboot.kafka;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ConsumerExample {

    @KafkaListener(topics = "#{'${io.confluent.developer.config.topic.name}'}")
    public void consume(final ConsumerRecord<Long, String> consumerRecord) {
        log.info("received message: \n  Key: {}\n  Value: {}", consumerRecord.key(), consumerRecord.value());
    }
}
