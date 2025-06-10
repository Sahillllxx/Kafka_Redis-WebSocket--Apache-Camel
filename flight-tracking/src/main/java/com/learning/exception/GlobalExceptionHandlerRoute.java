package com.learning.exception;

import com.learning.utils.KafkaEndpointBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandlerRoute extends RouteBuilder {

    private final KafkaEndpointBuilder builder;

    @Override
    public void configure() throws Exception {

        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3)
                .redeliveryDelay(2000)
                .retryAttemptedLogLevel(LoggingLevel.WARN));

        onException(Exception.class)
                .log(LoggingLevel.ERROR, log, "Exception: ${exception.message}")
                .handled(true)
                .to(StaticEndpointBuilders.log("generic-exception-logger"));

        onException(org.springframework.data.redis.RedisConnectionFailureException.class)
                .log(LoggingLevel.ERROR, log, "Redis Connection Failure: ${exception.message}")
                .handled(true)
                .to(StaticEndpointBuilders.log("redis-connection-exception-logger"));
    }
}
