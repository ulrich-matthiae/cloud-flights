package com.ulrich.matthiae.spring.clouddemo.booking.client.flight;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service")
public interface FlightServiceClient {

    @GetMapping("/flights/{id}")
    Flight getFlightById(@PathVariable("id") Integer id);

}
