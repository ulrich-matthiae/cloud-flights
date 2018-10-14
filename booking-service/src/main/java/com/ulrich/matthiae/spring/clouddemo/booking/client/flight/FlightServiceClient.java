package com.ulrich.matthiae.spring.clouddemo.booking.client.flight;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "flight-service")
public interface FlightServiceClient {

    @GetMapping("/flight-service/flights/")
    List<Flight> getAvailableFlights(@Param("flightDate")LocalDate flightDate);
}
