package com.ulrich.matthiae.spring.clouddemo.cost;

import com.ulrich.matthiae.spring.clouddemo.cost.models.Cost;
import com.ulrich.matthiae.spring.clouddemo.cost.models.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

@RefreshScope
@RestController
@RequestMapping(value = "/cost")
public class CostController {

    private static final Long LARGE_AIRPORT_FEE = 200L;
    private static final Long MULTIPLIER_DAYS_BOUNDARY = 90L;
    private static final BigDecimal PRICE_FLOOR = BigDecimal.valueOf(650);
    private static final String DEFAULT_CURRENCY = "ZAR";

    @Value("${dailyPriceIncrease}")
    private Long dailyPriceIncrease;

    private final Environment environment;

    @Autowired
    public CostController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping(value = "/flight")
    public ResponseEntity<Cost> getFlightCost(
            @RequestParam Location origin,
            @RequestParam Location destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate) throws InterruptedException {
        LocalDate now = LocalDate.now();
        BigDecimal currentCost = PRICE_FLOOR;
        Integer localServerPort = Integer.parseInt(environment.getProperty("local.server.port"));

        if ((DAYS.between(now, flightDate) < MULTIPLIER_DAYS_BOUNDARY)) {
            currentCost = PRICE_FLOOR.add(BigDecimal.valueOf((MULTIPLIER_DAYS_BOUNDARY - DAYS.between(now, flightDate)) * dailyPriceIncrease));
        }
        if (origin.equals(Location.JOHANNESBURG) || destination.equals(Location.JOHANNESBURG)) {
            currentCost = currentCost.add(BigDecimal.valueOf(LARGE_AIRPORT_FEE));
        }
        // Demonstrate ribbon & hystrix timout failure - by default hystrix will time out after 1s
        Thread.sleep(getLatency());

        return ResponseEntity.ok(new Cost(currentCost, DEFAULT_CURRENCY, localServerPort));
    }

    @RequestMapping(value = "/fallback", method = RequestMethod.GET)
    public ResponseEntity<Cost> fallback() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    }

    private int getLatency() {
        Random r = new Random();
        int low = 0;
        int high = 100;
        return r.nextInt(high - low) + low;
    }

}
