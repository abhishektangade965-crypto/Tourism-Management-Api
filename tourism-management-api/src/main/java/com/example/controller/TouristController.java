package com.example.controller;

import com.example.entity.Tourist;
import com.example.dto.TouristDTO;
import com.example.service.TouristService;
import com.example.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tourists")
@CrossOrigin(origins = "*")
@Slf4j
public class TouristController {
    
    @Autowired
    private TouristService touristService;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerTourist(@Valid @RequestBody TouristDTO touristDTO) {
        log.info("REST request to register tourist: {} {}", touristDTO.getFirstName(), touristDTO.getLastName());
        Tourist tourist = touristService.registerTourist(touristDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Tourist registered successfully", tourist));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTouristById(@PathVariable Long id) {
        log.info("REST request to get tourist by ID: {}", id);
        Tourist tourist = touristService.getTouristById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Tourist retrieved successfully", tourist));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse> getAllTourists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        log.info("REST request to get all tourists. Page: {}, Size: {}, SortBy: {}, Direction: {}", page, size, sortBy, direction);
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        Page<Tourist> tourists = touristService.getAllTourists(pageable);
        return ResponseEntity.ok(new ApiResponse(true, "All tourists retrieved successfully", tourists));
    }
    
    @GetMapping("/country/{country}")
    public ResponseEntity<ApiResponse> getTouristsByCountry(@PathVariable String country) {
        log.info("REST request to get tourists by country: {}", country);
        List<Tourist> tourists = touristService.getTouristsByCountry(country);
        return ResponseEntity.ok(new ApiResponse(true, "Tourists from country retrieved", tourists));
    }

    @GetMapping("/search/country")
    public ResponseEntity<ApiResponse> searchTouristsByCountry(@RequestParam String country) {
        log.info("REST request to search tourists by country parameter: {}", country);
        List<Tourist> tourists = touristService.getTouristsByCountry(country);
        return ResponseEntity.ok(new ApiResponse(true, "Tourists from country retrieved", tourists));
    }
    
    @GetMapping("/package/{tourPackage}")
    public ResponseEntity<ApiResponse> getTouristsByTourPackage(@PathVariable String tourPackage) {
        log.info("REST request to get tourists by tour package: {}", tourPackage);
        List<Tourist> tourists = touristService.getTouristsByTourPackage(tourPackage);
        return ResponseEntity.ok(new ApiResponse(true, "Tourists by tour package retrieved", tourists));
    }
    
    @GetMapping("/booking-status/{status}")
    public ResponseEntity<ApiResponse> getTouristsByBookingStatus(@PathVariable String status) {
        log.info("REST request to get tourists by booking status: {}", status);
        List<Tourist> tourists = touristService.getTouristsByBookingStatus(status);
        return ResponseEntity.ok(new ApiResponse(true, "Tourists by booking status retrieved", tourists));
    }
    
    @GetMapping("/active/all")
    public ResponseEntity<ApiResponse> getActiveTourists() {
        log.info("REST request to get all active tourists");
        List<Tourist> tourists = touristService.getActiveTourists();
        return ResponseEntity.ok(new ApiResponse(true, "Active tourists retrieved", tourists));
    }
    
    @GetMapping("/inactive/all")
    public ResponseEntity<ApiResponse> getInactiveTourists() {
        log.info("REST request to get all inactive tourists");
        List<Tourist> tourists = touristService.getInactiveTourists();
        return ResponseEntity.ok(new ApiResponse(true, "Inactive tourists retrieved", tourists));
    }
    
    @GetMapping("/cost/{minCost}")
    public ResponseEntity<ApiResponse> getTouristsByCost(@PathVariable Double minCost) {
        log.info("REST request to get tourists with cost greater than: {}", minCost);
        List<Tourist> tourists = touristService.getTouristsByCost(minCost);
        return ResponseEntity.ok(new ApiResponse(true, "Tourists filtered by cost", tourists));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTourist(@PathVariable Long id, @Valid @RequestBody TouristDTO touristDTO) {
        log.info("REST request to update tourist with ID: {}", id);
        Tourist tourist = touristService.updateTourist(id, touristDTO);
        return ResponseEntity.ok(new ApiResponse(true, "Tourist updated successfully", tourist));
    }
    
    @PutMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse> confirmBooking(@PathVariable Long id) {
        log.info("REST request to confirm booking for tourist ID: {}", id);
        Tourist tourist = touristService.confirmBooking(id);
        return ResponseEntity.ok(new ApiResponse(true, "Booking confirmed successfully", tourist));
    }
    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse> cancelBooking(@PathVariable Long id) {
        log.info("REST request to cancel booking for tourist ID: {}", id);
        touristService.cancelBooking(id);
        return ResponseEntity.ok(new ApiResponse(true, "Booking cancelled successfully", null));
    }
    
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<ApiResponse> updateBookingStatus(@PathVariable Long id, @PathVariable String status) {
        log.info("REST request to update booking status for tourist ID: {} to {}", id, status);
        Tourist tourist = touristService.updateBookingStatus(id, status);
        return ResponseEntity.ok(new ApiResponse(true, "Booking status updated successfully", tourist));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTourist(@PathVariable Long id) {
        log.info("REST request to soft delete tourist ID: {}", id);
        touristService.cancelBooking(id);
        return ResponseEntity.ok(new ApiResponse(true, "Tourist record deactivated", null));
    }
    
    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<ApiResponse> deleteTouristPermanently(@PathVariable Long id) {
        log.info("REST request to permanently delete tourist ID: {}", id);
        touristService.deleteTouristPermanently(id);
        return ResponseEntity.ok(new ApiResponse(true, "Tourist record deleted permanently", null));
    }
    
    @GetMapping("/count/active")
    public ResponseEntity<ApiResponse> countActiveTourists() {
        log.info("REST request to count active tourists");
        Long count = touristService.countActiveTourists();
        Map<String, Object> response = new HashMap<>();
        response.put("activeTourists", count);
        return ResponseEntity.ok(new ApiResponse(true, "Active tourist count", response));
    }
    
    @GetMapping("/count/country/{country}")
    public ResponseEntity<ApiResponse> countByCountry(@PathVariable String country) {
        log.info("REST request to count tourists from country: {}", country);
        Long count = touristService.countTouristsByCountry(country);
        Map<String, Object> response = new HashMap<>();
        response.put("country", country);
        response.put("count", count);
        return ResponseEntity.ok(new ApiResponse(true, "Country tourist count", response));
    }
    
    @GetMapping("/count/package/{tourPackage}")
    public ResponseEntity<ApiResponse> countByPackage(@PathVariable String tourPackage) {
        log.info("REST request to count tourists for tour package: {}", tourPackage);
        Long count = touristService.countTouristsByTourPackage(tourPackage);
        Map<String, Object> response = new HashMap<>();
        response.put("tourPackage", tourPackage);
        response.put("count", count);
        return ResponseEntity.ok(new ApiResponse(true, "Tour package tourist count", response));
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse> getTouristsByEmail(@PathVariable String email) {
        log.info("REST request to get tourists by email: {}", email);
        List<Tourist> tourists = touristService.getTouristsByEmail(email);
        return ResponseEntity.ok(new ApiResponse(true, "Tourists by email retrieved", tourists));
    }
}
