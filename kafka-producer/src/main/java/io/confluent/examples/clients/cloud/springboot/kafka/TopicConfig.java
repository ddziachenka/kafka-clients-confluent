package io.confluent.examples.clients.cloud.springboot.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Value("${io.confluent.developer.config.topic.name}")
    private String topicName;
    @Value("${io.confluent.developer.config.topic.partitions}")
    private int numPartitions;
    @Value("${io.confluent.developer.config.topic.replicas}")
    private int replicas;

    @Bean
    NewTopic getNewTopic() {
        return new NewTopic(topicName, numPartitions, (short) replicas);
    }
}
