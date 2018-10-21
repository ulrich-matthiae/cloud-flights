package com.ulrich.matthiae.spring.clouddemo.flight;

import com.ulrich.matthiae.spring.clouddemo.flight.model.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends MongoRepository<Flight, Integer> {

    @RestResource
    List<Flight> findAllByFlightDate(@Param("flightDate") LocalDate flightDate);

    @RestResource
    Optional<Flight> findById(@Param("id") Integer id);
}
