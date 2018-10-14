package com.ulrich.matthiae.spring.clouddemo.flight.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    private LocalDate flightDate;

    @Enumerated(value = EnumType.STRING)
    private Location origin;

    @Enumerated(value = EnumType.STRING)
    private Location destination;

    private Integer seatsAvailable;

    private Cost cost;
}
