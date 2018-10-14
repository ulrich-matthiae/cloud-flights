package com.ulrich.matthiae.spring.clouddemo.flight;

import com.ulrich.matthiae.spring.clouddemo.flight.client.cost.CostServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RepositoryRestController
public class FlightController {
    private final FlightRepository flightRepository;
    private final CostServiceClient costServiceClient;

    @Autowired
    public FlightController(FlightRepository flightRepository, CostServiceClient costServiceClient) {
        this.flightRepository = flightRepository;
        this.costServiceClient = costServiceClient;
    }

    @RequestMapping(method = GET, value = "/flights")
    public ResponseEntity<?> getFlights(@RequestParam(value = "flightDate") LocalDate flightDate) {
        flightRepository.findAllByFlightDate(flightDate);

        return ResponseEntity.ok().build();
    }
}
