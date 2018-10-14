package com.ulrich.matthiae.spring.clouddemo.booking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private Integer idNumber;

    private Integer passportNumber;

    private String name;

    private String surname;

    private Gender gender;

    private Integer flightId;

    private BigDecimal totalCost;
}
