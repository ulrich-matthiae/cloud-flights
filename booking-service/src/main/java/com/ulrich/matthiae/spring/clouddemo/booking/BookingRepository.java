package com.ulrich.matthiae.spring.clouddemo.booking;

import com.ulrich.matthiae.spring.clouddemo.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @RestResource
    List<Booking> findAllByUserId(@Param("userId") Integer userId);
}
