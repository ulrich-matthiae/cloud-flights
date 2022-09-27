package com.ulrich.matthiae.spring.clouddemo.flight.client.cost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CostService {

    private CostServiceClient costServiceClient;
    private Logger LOG = LoggerFactory.getLogger(CostService.class);

    public CostService(@Autowired CostServiceClient costServiceClient) {
        this.costServiceClient = costServiceClient;
    }

    public Cost getFlightCost(Location origin, Location destination, LocalDate flightDate) {
        System.err.println("Fetch Flights");
        return costServiceClient.getFlightCost(origin, destination, flightDate);
    }

    public Cost fallbackAction(Location origin, Location destination, LocalDate flightDate) {
        LOG.error("fallback action cannot fetch cost for from {} to {} date {}", origin, destination, flightDate);
        System.err.println("Fetch Flights Error handler");
        return new Cost();
    }
}