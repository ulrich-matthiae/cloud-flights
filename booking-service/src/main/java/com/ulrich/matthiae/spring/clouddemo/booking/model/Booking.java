package com.ulrich.matthiae.spring.clouddemo.booking.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Booking {

    @Id
    private Integer id;

    private String idNumber;

    private String passportNumber;

    private String name;

    private String surname;

    private Gender gender;

    private Integer flightId;

    private BigDecimal totalCost;
}
