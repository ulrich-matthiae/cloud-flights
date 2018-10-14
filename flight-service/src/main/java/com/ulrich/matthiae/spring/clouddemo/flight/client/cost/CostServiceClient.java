package com.ulrich.matthiae.spring.clouddemo.flight.client.cost;

import feign.Param;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "cost-service")
public interface CostServiceClient {

    @GetMapping("/cost-service/cost/flight")
    Cost getFlightCost(@Param("origin") Location origin, @Param("destination") Location destination, @Param("flightDate")LocalDate flightDate);
}
