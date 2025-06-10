package com.learning.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.dto.FlightStatusDTO;
import com.learning.model.FlightStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FlightDataProcessor implements Processor {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {

        FlightStatus flight = exchange.getIn().getBody(FlightStatus.class);

        log.info("Processing flight data: {}", flight);


        FlightStatusDTO dto = new FlightStatusDTO(
            flight.getFlightNumber(),
            flight.getDeparture(),
            flight.getArrival(),
            flight.getDepartureTime(),
            flight.getArrivalTime(),
            flight.getStatus()
        );

        log.info("Mapped flight data to DTO: {}", dto);

        String json = objectMapper.writeValueAsString(dto);
        log.info("Converted DTO to JSON: {}", json);

        String redisKey = "flight:" + flight.getFlightNumber();
        redisTemplate.opsForValue().set(redisKey, json);

        log.info("Stored flight data in Redis with key: {}", redisKey);

        exchange.getIn().setBody(json);
    }
}
