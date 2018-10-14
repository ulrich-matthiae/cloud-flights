package com.ulrich.matthiae.spring.clouddemo.cost;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ulrich.matthiae.spring.clouddemo.cost.models.Cost;
import com.ulrich.matthiae.spring.clouddemo.cost.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping(value = "/cost")
public class CostController {

    private static final Long DAILY_PRICE_INCREASE = 10L;
    private static final Long LARGE_AIRPORT_FEE = 200L;
    private static final Long MULTIPLIER_DAYS_BOUNDARY = 90L;
    private static final BigDecimal PRICE_FLOOR = BigDecimal.valueOf(650);

    private final Environment environment;

    @Autowired
    public CostController(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping(value = "/flight", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fetchDefaultCost")
    public Cost getFlightCost(@RequestParam Location origin, @RequestParam Location destination, @RequestParam LocalDate flightDate) {
        LocalDate now = LocalDate.now();
        BigDecimal currentCost = PRICE_FLOOR;
        Integer localServerPort = Integer.parseInt(environment.getProperty("local.server.port"));

        if ((DAYS.between(flightDate, now) < MULTIPLIER_DAYS_BOUNDARY)) {
            currentCost = PRICE_FLOOR.add(BigDecimal.valueOf(MULTIPLIER_DAYS_BOUNDARY - DAYS.between(flightDate, now) * DAILY_PRICE_INCREASE));
        }
        if(origin.equals(Location.JOHANNESBURG) || destination.equals(Location.JOHANNESBURG)){
            currentCost = currentCost.add(BigDecimal.valueOf(LARGE_AIRPORT_FEE));
        }

        return new Cost(currentCost, localServerPort);
    }

    private Cost fetchDefaultCost() {
        BigDecimal priceCeiling = PRICE_FLOOR.add(BigDecimal.valueOf(MULTIPLIER_DAYS_BOUNDARY * DAILY_PRICE_INCREASE));
        return new Cost(priceCeiling, 0);
    }
}
