package com.learning.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "camel.component.kafka")
public class KafkaProperties {
    private String brokers;
    private String inputTopic;
//    private String dlqTopic;
}
