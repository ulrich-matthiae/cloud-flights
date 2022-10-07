package com.ulrich.matthiae.spring.clouddemo.booking.model;

import com.ulrich.matthiae.spring.clouddemo.booking.client.flight.Flight;
import lombok.Data;

@Data
public class BookingEvent {

    private final Booking booking;
    private final Flight flight;
}
