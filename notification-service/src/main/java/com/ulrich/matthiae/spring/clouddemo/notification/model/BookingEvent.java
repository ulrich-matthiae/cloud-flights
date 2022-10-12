package com.ulrich.matthiae.spring.clouddemo.notification.model;

import lombok.Data;

@Data
public class BookingEvent {

    private final Booking booking;
    private final Flight flight;
}
