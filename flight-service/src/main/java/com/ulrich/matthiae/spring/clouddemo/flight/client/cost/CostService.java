package com.ulrich.matthiae.spring.clouddemo.flight.client.cost;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class CostService {

    private CostServiceClient costServiceClient;

    public CostService(@Autowired CostServiceClient costServiceClient) {
        this.costServiceClient = costServiceClient;
    }

    public Cost getFlightCost(Location origin, Location destination, LocalDate flightDate) {
        log.info("Get Flight cost");
        return costServiceClient.getFlightCost(origin, destination, flightDate);
    }

    public Cost fallbackAction(Location origin, Location destination, LocalDate flightDate) {
        log.error("fallback action cannot fetch cost for from {} to {} date {}", origin, destination, flightDate);
        return new Cost();
    }
}