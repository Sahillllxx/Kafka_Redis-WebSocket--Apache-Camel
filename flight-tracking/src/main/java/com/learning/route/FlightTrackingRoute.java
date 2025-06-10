package com.learning.route;

import com.learning.utils.FlightWebSocketHandler;
import com.learning.model.FlightStatus;
import com.learning.process.FlightDataProcessor;
import com.learning.utils.KafkaEndpointBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class FlightTrackingRoute extends RouteBuilder {

    public static final String FLIGHT_TRACKING_ROUTE_ID = "flight-tracking-route";

    private final FlightDataProcessor processor;
    private final FlightWebSocketHandler webSocketHandler;
    private final KafkaEndpointBuilder endpointBuilder;

    @Override
    public void configure() {

        from(endpointBuilder.kafkaInputUri())
                .routeId(FLIGHT_TRACKING_ROUTE_ID)
                .log("Received message from Kafka: ${body}")

                .unmarshal()
                .json(JsonLibrary.Jackson, FlightStatus.class)
                .log("Unmarshalled JSON to FlightStatus: ${body}")

                .process(processor)
                .log("Mapped flight data and pushed to Redis: ${body}")

                .process(exchange -> {
                    String message = exchange.getIn().getBody(String.class);

                    webSocketHandler.sendMessageToClients(message);
                    log.info("Sent message to WebSocket clients: {}", message);
                });
    }
}
