package com.ulrich.matthiae.spring.clouddemo.flight;

import com.ulrich.matthiae.spring.clouddemo.flight.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    @RestResource
    List<Flight> findAllByFlightDate(@Param("flightDate") LocalDate flightDate);
}
