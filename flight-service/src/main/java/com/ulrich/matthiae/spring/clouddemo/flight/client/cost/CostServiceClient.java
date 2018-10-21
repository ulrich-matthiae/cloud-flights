package com.ulrich.matthiae.spring.clouddemo.flight.client.cost;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "cost-service")
public interface CostServiceClient {

    @GetMapping("/cost-service/cost/flight")
    Cost getFlightCost(
            @RequestParam("origin") Location origin,
            @RequestParam("destination") Location destination,
            @RequestParam("flightDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate);
}
