package com.ulrich.matthiae.spring.clouddemo.booking.client.flight;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "flight-service")
@RibbonClient(name = "flight-service")
@Repository
public interface FlightServiceClient {

    @GetMapping("/flight-service/flights/{id}")
    Flight getFlightById(@PathVariable("id") Integer id);

}
