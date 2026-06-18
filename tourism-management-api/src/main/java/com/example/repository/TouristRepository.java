package com.example.repository;

import com.example.entity.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TouristRepository extends JpaRepository<Tourist, Long> {
    
    List<Tourist> findByEmail(String email);
    
    List<Tourist> findByPassportNumber(String passportNumber);
    
    List<Tourist> findByCountry(String country);
    
    List<Tourist> findByTourPackage(String tourPackage);
    
    List<Tourist> findByBookingStatus(String bookingStatus);
    
    List<Tourist> findByIsActiveTrue();
    
    List<Tourist> findByIsActiveFalse();
    
    @Query("SELECT t FROM Tourist t WHERE t.totalCost > :minCost")
    List<Tourist> findTouristsByCostGreaterThan(@Param("minCost") Double minCost);
    
    @Query("SELECT t FROM Tourist t WHERE t.tourPackage = :tourPackage AND t.bookingStatus = 'CONFIRMED'")
    List<Tourist> findConfirmedTouristsByPackage(@Param("tourPackage") String tourPackage);
    
    Long countByCountry(String country);
    
    Long countByTourPackage(String tourPackage);
    
    Long countByBookingStatus(String bookingStatus);
    
    @Query("SELECT COUNT(t) FROM Tourist t WHERE t.isActive = true")
    Long countActiveTourists();
}
