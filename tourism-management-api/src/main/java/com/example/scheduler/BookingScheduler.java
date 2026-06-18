package com.example.scheduler;

import com.example.service.TouristService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingScheduler {

    @Autowired
    private TouristService touristService;

    // Run daily at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleBookingExpiration() {
        log.info("Scheduled task running: Expiring bookings...");
        try {
            touristService.expireBookings();
            log.info("Scheduled task finished successfully.");
        } catch (Exception e) {
            log.error("Failed to run scheduled booking expiration task", e);
        }
    }
}
