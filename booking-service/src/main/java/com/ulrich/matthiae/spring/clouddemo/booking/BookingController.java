package com.ulrich.matthiae.spring.clouddemo.booking;

import com.ulrich.matthiae.spring.clouddemo.booking.client.flight.Flight;
import com.ulrich.matthiae.spring.clouddemo.booking.client.flight.FlightServiceClient;
import com.ulrich.matthiae.spring.clouddemo.booking.model.Booking;
import com.ulrich.matthiae.spring.clouddemo.booking.model.BookingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@Slf4j
@RepositoryRestController
public class BookingController {
    private final BookingRepository bookingRepository;
    private final FlightServiceClient flightServiceClient;
    private final StreamBridge streamBridge;

    @Autowired
    public BookingController(BookingRepository bookingRepository, FlightServiceClient flightServiceClient, StreamBridge streamBridge) {
        this.bookingRepository = bookingRepository;
        this.flightServiceClient = flightServiceClient;
        this.streamBridge = streamBridge;
    }

    @PostMapping(value = "/bookings")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        log.info("Creating booking");
        Flight flight = flightServiceClient.getFlightById(booking.getFlightId());

        booking.setTotalCost(flight.getPrice());
        Booking createdBooking = bookingRepository.save(booking);

        log.info("Sending notification");
        streamBridge.send("bookingEvents-out-0", new BookingEvent(createdBooking, flight));

        return ResponseEntity.created(URI.create("/bookings/" + createdBooking.getId())).build();
    }
}