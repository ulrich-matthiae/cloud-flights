package com.ulrich.matthiae.spring.clouddemo.flight;

import com.ulrich.matthiae.spring.clouddemo.flight.client.cost.Cost;
import com.ulrich.matthiae.spring.clouddemo.flight.client.cost.CostService;
import com.ulrich.matthiae.spring.clouddemo.flight.model.Flight;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RepositoryRestController
public class FlightController {
    private final FlightRepository flightRepository;
    private final CostService costService;

    @Autowired
    public FlightController(FlightRepository flightRepository, CostService costService) {
        this.flightRepository = flightRepository;
        this.costService = costService;
    }

    @RequestMapping(method = GET, value = "/flights")
    @CircuitBreaker(name = "flightService", fallbackMethod = "fallback")
    public ResponseEntity<?> getFlightsByDate(@RequestParam(value = "flightDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate) {
        List<Flight> flightsByDate = flightRepository.findAllByFlightDate(flightDate);
        for (Flight flight : flightsByDate) {
            Cost flightCost = costService.getFlightCost(
                    flight.getOrigin().getCostLocation(),
                    flight.getDestination().getCostLocation(),
                    flightDate);
            flight.setPrice(flightCost.getPrice());
            flight.setCostServicePort(flightCost.getLocalServerPort());
        }

        return new ResponseEntity<List>(flightsByDate, HttpStatus.OK);
    }

    public ResponseEntity<?> fallback(LocalDate flightDate, Exception e){
        String errorString = "Error fetching flight cost for flight "+flightDate;
        log.error(errorString, e);
        return new ResponseEntity<>(errorString,HttpStatus.BAD_GATEWAY);
    }

    @RequestMapping(method = GET, value = "/flights/{id}")
    public ResponseEntity<?> getFlightById(@PathVariable("id") Integer id) {
        log.info("Calling get flight by id");
        Optional<Flight> flightOptional = flightRepository.findById(id);

        if (flightOptional.isPresent()) {
            Flight flight = flightOptional.get();
            Cost flightCost = costService.getFlightCost(flight.getOrigin().getCostLocation(), flight.getDestination().getCostLocation(), flight.getFlightDate());

            flight.setPrice(flightCost.getPrice());
            flight.setCostServicePort(flightCost.getLocalServerPort());

            return new ResponseEntity<>(flight, HttpStatus.OK);
        }

        return ResponseEntity.notFound().build();
    }
}
