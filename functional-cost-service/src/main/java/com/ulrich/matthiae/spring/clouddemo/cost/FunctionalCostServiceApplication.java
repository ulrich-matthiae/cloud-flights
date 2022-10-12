package com.ulrich.matthiae.spring.clouddemo.cost;

import com.ulrich.matthiae.spring.clouddemo.cost.models.Cost;
import com.ulrich.matthiae.spring.clouddemo.cost.models.CostRequest;
import com.ulrich.matthiae.spring.clouddemo.cost.models.Location;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.DAYS;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class FunctionalCostServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunctionalCostServiceApplication.class, args);
    }

    private static final Long LARGE_AIRPORT_FEE = 200L;
    private static final Long MULTIPLIER_DAYS_BOUNDARY = 90L;
    private static final BigDecimal PRICE_FLOOR = BigDecimal.valueOf(650);
    private static final String DEFAULT_CURRENCY = "ZAR";

    @Value("${dailyPriceIncrease}")
    private Long dailyPriceIncrease;

    @Bean
    @RouterOperation(operation = @Operation(description = "Calculcate flight cost", operationId = "cost-flight", tags = "flights"))
    public Function<CostRequest, Cost> flight() {
        return request -> {
            LocalDate now = LocalDate.now();
            BigDecimal currentCost = PRICE_FLOOR;

            if ((DAYS.between(now, request.getFlightDate()) < MULTIPLIER_DAYS_BOUNDARY)) {
                currentCost = PRICE_FLOOR.add(BigDecimal.valueOf((MULTIPLIER_DAYS_BOUNDARY - DAYS.between(now, request.getFlightDate())) * dailyPriceIncrease));
            }
            if (request.getOrigin().equals(Location.JOHANNESBURG) || request.getDestination().equals(Location.JOHANNESBURG)) {
                currentCost = currentCost.add(BigDecimal.valueOf(LARGE_AIRPORT_FEE));
            }

            return new Cost(currentCost, DEFAULT_CURRENCY);
        };
    }
}