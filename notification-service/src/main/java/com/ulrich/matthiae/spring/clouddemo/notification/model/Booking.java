package com.ulrich.matthiae.spring.clouddemo.notification.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Booking {

    private Integer id;

    private String idNumber;

    private String passportNumber;

    private String name;

    private String surname;

    private String gender;

    private Integer flightId;

    private BigDecimal totalCost;
}
