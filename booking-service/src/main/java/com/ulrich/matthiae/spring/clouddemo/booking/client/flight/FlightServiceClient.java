package com.ulrich.matthiae.spring.clouddemo.booking.client.flight;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "flight-service")
public interface FlightServiceClient {

    @GetMapping("/flight-service/flights/{id}")
    Flight getFlightById(@PathVariable("id") Integer id);
}
