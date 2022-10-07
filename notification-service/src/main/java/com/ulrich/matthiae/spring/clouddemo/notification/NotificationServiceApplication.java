package com.ulrich.matthiae.spring.clouddemo.notification;

import com.ulrich.matthiae.spring.clouddemo.notification.model.BookingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @Bean
    public Consumer<BookingEvent> bookingEventsConsumer() {
        return event -> {
            log.info("Sending booking confirmation for booking id {} from {} to {} for {}", event.getBooking().getId(), event.getFlight().getOrigin(), event.getFlight().getDestination(), event.getBooking().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };
    }
}
