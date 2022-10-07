package com.ulrich.matthiae.spring.clouddemo.notification.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Flight {
    @Id
    private Integer id;

    private LocalDate flightDate;

    private String origin;

    private String destination;

    private Integer seatsAvailable;

    private BigDecimal price;

    private Integer costServicePort;
}
