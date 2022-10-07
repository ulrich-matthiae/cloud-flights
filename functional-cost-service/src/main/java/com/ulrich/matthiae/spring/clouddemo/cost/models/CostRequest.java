package com.ulrich.matthiae.spring.clouddemo.cost.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CostRequest {

    private final Location origin;
    private final Location destination;
    private final @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate;
}
