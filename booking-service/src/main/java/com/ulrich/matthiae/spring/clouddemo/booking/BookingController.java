package com.ulrich.matthiae.spring.clouddemo.booking;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ulrich.matthiae.spring.clouddemo.booking.client.flight.Flight;
import com.ulrich.matthiae.spring.clouddemo.booking.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RepositoryRestController
public class BookingController {
    private final BookingRepository bookingRepository;
    private final FlightService flightServiceClient;

    @Autowired
    public BookingController(BookingRepository bookingRepository, FlightService flightServiceClient) {
        this.bookingRepository = bookingRepository;
        this.flightServiceClient = flightServiceClient;
    }

    @HystrixCommand
    @RequestMapping(method = POST, value = "/bookings")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        Flight flight = flightServiceClient.getFlightById(booking.getFlightId());

        booking.setTotalCost(flight.getPrice());
        Booking createdBooking = bookingRepository.save(booking);

        return ResponseEntity.created(URI.create("/bookings/" + createdBooking.getId())).build();
    }

    @GetMapping(value = "/bookings/flight/{id}")
    public ResponseEntity<?> getFlight(@PathVariable("id") Integer flightId){
        return ResponseEntity.ok(flightServiceClient.getFlightById(flightId));
    }

}