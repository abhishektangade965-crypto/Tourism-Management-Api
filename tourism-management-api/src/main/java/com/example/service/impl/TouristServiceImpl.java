package com.example.service.impl;

import com.example.entity.Tourist;
import com.example.dto.TouristDTO;
import com.example.repository.TouristRepository;
import com.example.service.TouristService;
import com.example.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.time.LocalDate;

@Service
@Transactional
public class TouristServiceImpl implements TouristService {
    
    @Autowired
    private TouristRepository touristRepository;
    
    @Override
    public Tourist registerTourist(TouristDTO touristDTO) {
        Tourist tourist = new Tourist();
        tourist.setFirstName(touristDTO.getFirstName());
        tourist.setLastName(touristDTO.getLastName());
        tourist.setEmail(touristDTO.getEmail());
        tourist.setPhone(touristDTO.getPhone());
        tourist.setCountry(touristDTO.getCountry());
        tourist.setPassportNumber(touristDTO.getPassportNumber());
        tourist.setTourPackage(touristDTO.getTourPackage());
        tourist.setCheckInDate(touristDTO.getCheckInDate());
        tourist.setCheckOutDate(touristDTO.getCheckOutDate());
        tourist.setRoomNumber(touristDTO.getRoomNumber());
        tourist.setTotalCost(touristDTO.getTotalCost());
        tourist.setIsActive(true);
        tourist.setBookingStatus("PENDING");
        
        return touristRepository.save(tourist);
    }
    
    @Override
    public Tourist updateTourist(Long id, TouristDTO touristDTO) {
        Tourist tourist = touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id: " + id));
        
        tourist.setFirstName(touristDTO.getFirstName());
        tourist.setLastName(touristDTO.getLastName());
        tourist.setEmail(touristDTO.getEmail());
        tourist.setPhone(touristDTO.getPhone());
        tourist.setCountry(touristDTO.getCountry());
        tourist.setPassportNumber(touristDTO.getPassportNumber());
        tourist.setTourPackage(touristDTO.getTourPackage());
        tourist.setCheckInDate(touristDTO.getCheckInDate());
        tourist.setCheckOutDate(touristDTO.getCheckOutDate());
        tourist.setRoomNumber(touristDTO.getRoomNumber());
        tourist.setTotalCost(touristDTO.getTotalCost());
        
        return touristRepository.save(tourist);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Tourist getTouristById(Long id) {
        return touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Tourist> getAllTourists(Pageable pageable) {
        return touristRepository.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Tourist> getTouristsByCountry(String country) {
        return touristRepository.findByCountry(country);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Tourist> getTouristsByTourPackage(String tourPackage) {
        return touristRepository.findByTourPackage(tourPackage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Tourist> getActiveTourists() {
        return touristRepository.findByIsActiveTrue();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Tourist> getInactiveTourists() {
        return touristRepository.findByIsActiveFalse();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Tourist> getTouristsByBookingStatus(String bookingStatus) {
        return touristRepository.findByBookingStatus(bookingStatus);
    }
    
    @Override
    public void cancelBooking(Long id) {
        Tourist tourist = touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id: " + id));
        tourist.setBookingStatus("CANCELLED");
        tourist.setIsActive(false);
        touristRepository.save(tourist);
    }
    
    @Override
    public void deleteTouristPermanently(Long id) {
        if (!touristRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tourist not found with id: " + id);
        }
        touristRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Tourist> getTouristsByCost(Double minCost) {
        return touristRepository.findTouristsByCostGreaterThan(minCost);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long countTouristsByCountry(String country) {
        return touristRepository.countByCountry(country);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long countTouristsByTourPackage(String tourPackage) {
        return touristRepository.countByTourPackage(tourPackage);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long countActiveTourists() {
        return touristRepository.countActiveTourists();
    }
    
    @Override
    public Tourist confirmBooking(Long id) {
        Tourist tourist = touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id: " + id));
        tourist.setBookingStatus("CONFIRMED");
        return touristRepository.save(tourist);
    }
    
    @Override
    public Tourist updateBookingStatus(Long id, String status) {
        Tourist tourist = touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id: " + id));
        
        if (!status.matches("CONFIRMED|PENDING|CANCELLED|UPCOMING|COMPLETED|EXPIRED")) {
            throw new IllegalArgumentException("Invalid booking status. Must be CONFIRMED, PENDING, CANCELLED, UPCOMING, COMPLETED, or EXPIRED");
        }
        
        tourist.setBookingStatus(status);
        return touristRepository.save(tourist);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Tourist> getTouristsByEmail(String email) {
        return touristRepository.findByEmail(email);
    }

    @Override
    public void expireBookings() {
        List<Tourist> activeTourists = touristRepository.findByIsActiveTrue();
        LocalDate today = LocalDate.now();
        for (Tourist tourist : activeTourists) {
            if (tourist.getCheckOutDate() != null) {
                try {
                    LocalDate checkOut = LocalDate.parse(tourist.getCheckOutDate());
                    if (checkOut.isBefore(today)) {
                        tourist.setIsActive(false);
                        if ("PENDING".equalsIgnoreCase(tourist.getBookingStatus())) {
                            tourist.setBookingStatus("EXPIRED");
                        } else if ("CONFIRMED".equalsIgnoreCase(tourist.getBookingStatus()) || "UPCOMING".equalsIgnoreCase(tourist.getBookingStatus())) {
                            tourist.setBookingStatus("COMPLETED");
                        } else {
                            tourist.setBookingStatus("EXPIRED");
                        }
                        touristRepository.save(tourist);
                    }
                } catch (Exception e) {
                    // Ignore parsing error for bad data in checkOutDate
                }
            }
        }
    }
}
