package com.ulrich.matthiae.spring.clouddemo.booking;

import com.ulrich.matthiae.spring.clouddemo.booking.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends MongoRepository<Booking, Integer> {
}
