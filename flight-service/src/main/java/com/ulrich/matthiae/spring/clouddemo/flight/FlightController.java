package com.ulrich.matthiae.spring.clouddemo.flight;

import com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Cost;
import com.ulrich.matthiae.spring.clouddemo.flight.client.cost.CostServiceClient;
import com.ulrich.matthiae.spring.clouddemo.flight.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> getFlightsByDate(@RequestParam(value = "flightDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate) {
        List<Flight> flightsByDate = flightRepository.findAllByFlightDate(flightDate);
        for (Flight flight : flightsByDate) {
            Cost flightCost = costServiceClient.getFlightCost(
                    flight.getOrigin().getCostLocation(),
                    flight.getDestination().getCostLocation(),
                    flightDate);
            flight.setPrice(flightCost.getPrice());
            flight.setCostServicePort(flightCost.getLocalServerPort());
        }


        return new ResponseEntity<List>(flightsByDate, HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "/flights/{id}")
    public ResponseEntity<?> getFlightById(@PathVariable("id") Integer id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);

        if (flightOptional.isPresent()) {
            Flight flight = flightOptional.get();
            Cost flightCost = costServiceClient.getFlightCost(flight.getOrigin().getCostLocation(), flight.getDestination().getCostLocation(), flight.getFlightDate());

            flight.setPrice(flightCost.getPrice());
            flight.setCostServicePort(flightCost.getLocalServerPort());

            return new ResponseEntity<>(flight, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}
