package io.confluent.examples.clients.cloud.springboot.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static java.util.stream.IntStream.range;

@Log4j2
@Component
@RequiredArgsConstructor
public class ProducerExample {

    private final KafkaTemplate<String, String> producer;
    private final NewTopic topic;

    @EventListener(ApplicationStartedEvent.class)
    public void produce() {
        range(0, 3).forEach(i -> {
            final String key = "Test key " + i;
            final String record = "Test record " + i;
            log.info("Producing record: {}\t{}", key, record);
            producer.send(topic.name(), key, record).addCallback(
                    result -> {
                        final RecordMetadata m;
                        if (result != null) {
                            m = result.getRecordMetadata();
                            log.info("Produced record to topic {} partition {} @ offset {}",
                                    m.topic(),
                                    m.partition(),
                                    m.offset());
                        }
                    },
                    exception -> log.error("Failed to produce to kafka", exception));
        });
        producer.flush();
        log.info("10 messages were produced to topic {}", topic.name());
    }

}