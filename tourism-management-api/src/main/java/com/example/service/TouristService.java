package com.example.service;

import com.example.entity.Tourist;
import com.example.dto.TouristDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TouristService {
    
    Tourist registerTourist(TouristDTO touristDTO);
    
    Tourist updateTourist(Long id, TouristDTO touristDTO);
    
    Tourist getTouristById(Long id);
    
    Page<Tourist> getAllTourists(Pageable pageable);
    
    List<Tourist> getTouristsByCountry(String country);
    
    List<Tourist> getTouristsByTourPackage(String tourPackage);
    
    List<Tourist> getActiveTourists();
    
    List<Tourist> getInactiveTourists();
    
    List<Tourist> getTouristsByBookingStatus(String bookingStatus);
    
    void cancelBooking(Long id);
    
    void deleteTouristPermanently(Long id);
    
    List<Tourist> getTouristsByCost(Double minCost);
    
    Long countTouristsByCountry(String country);
    
    Long countTouristsByTourPackage(String tourPackage);
    
    Long countActiveTourists();
    
    Tourist confirmBooking(Long id);
    
    Tourist updateBookingStatus(Long id, String status);
    
    List<Tourist> getTouristsByEmail(String email);
    
    void expireBookings();
}
