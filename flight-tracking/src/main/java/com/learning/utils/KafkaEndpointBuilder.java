package com.learning.utils;

import com.learning.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEndpointBuilder {

    private final KafkaProperties properties;

    public String kafkaInputUri() {
        return StaticEndpointBuilders
                .kafka(properties.getInputTopic())
                .brokers(properties.getBrokers())
                .getUri();
    }


//    public String directDeadLetterQueue() {
//        return StaticEndpointBuilders
//                .direct("deadLetterQueue")
//                .getUri();
//    }
//
//    public String kafkaDlqUri() {
//        return StaticEndpointBuilders
//                .kafka(properties.getDlqTopic())
//                .brokers(properties.getBrokers())
//                .getUri();
//    }

}
