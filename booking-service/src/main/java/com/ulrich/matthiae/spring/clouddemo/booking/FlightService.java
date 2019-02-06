package com.ulrich.matthiae.spring.clouddemo.booking;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.ulrich.matthiae.spring.clouddemo.booking.client.flight.Flight;
import com.ulrich.matthiae.spring.clouddemo.booking.client.flight.FlightServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FlightService {

    private Logger LOG = LoggerFactory.getLogger(FlightService.class);

    private FlightServiceClient flightServiceClient;

    @Autowired
    public FlightService(FlightServiceClient flightServiceClient){
        this.flightServiceClient = flightServiceClient;
    }

    @HystrixCommand(fallbackMethod = "fallbackAction", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
    })
    public Flight getFlightById(Integer id) {
        System.err.println("Fetch handler");
        LOG.debug("fetch flight with id {id}", id);
        if(id < 0){
            throw new RuntimeException("Sample Error");
        }
        return flightServiceClient.getFlightById(id);
    }

    @SuppressWarnings("unused")
    protected Flight fallbackAction(Integer id){
        LOG.error("fallback action cannot fetch flight {id}", id);
        System.err.println("Error handler");
        return new Flight();
    }
}