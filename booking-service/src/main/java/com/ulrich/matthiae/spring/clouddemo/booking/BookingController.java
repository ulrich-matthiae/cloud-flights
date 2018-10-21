package com.ulrich.matthiae.spring.clouddemo.booking;

import com.ulrich.matthiae.spring.clouddemo.booking.client.flight.Flight;
import com.ulrich.matthiae.spring.clouddemo.booking.client.flight.FlightServiceClient;
import com.ulrich.matthiae.spring.clouddemo.booking.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RepositoryRestController
public class BookingController {
    private final BookingRepository bookingRepository;
    private final FlightServiceClient flightServiceClient;

    @Autowired
    public BookingController(BookingRepository bookingRepository, FlightServiceClient flightServiceClient) {
        this.bookingRepository = bookingRepository;
        this.flightServiceClient = flightServiceClient;
    }

    @RequestMapping(method = POST, value = "/bookings")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        Flight flight = flightServiceClient.getFlightById(booking.getFlightId());

        booking.setTotalCost(flight.getPrice());
        Booking createdBooking = bookingRepository.save(booking);

        return ResponseEntity.created(URI.create("/bookings/" + createdBooking.getId())).build();
    }
}
