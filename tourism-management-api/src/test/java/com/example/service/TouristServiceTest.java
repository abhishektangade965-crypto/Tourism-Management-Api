package com.example.service;

import com.example.dto.TouristDTO;
import com.example.entity.Tourist;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.TouristRepository;
import com.example.service.impl.TouristServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TouristServiceTest {
    
    @Mock
    private TouristRepository touristRepository;
    
    @InjectMocks
    private TouristServiceImpl touristService;
    
    private Tourist testTourist;
    private TouristDTO testTouristDTO;
    
    @BeforeEach
    public void setUp() {
        testTourist = new Tourist();
        testTourist.setId(1L);
        testTourist.setFirstName("John");
        testTourist.setLastName("Doe");
        testTourist.setEmail("john.doe@example.com");
        testTourist.setPhone("9876543210");
        testTourist.setCountry("USA");
        testTourist.setPassportNumber("US12345678");
        testTourist.setTourPackage("Gold Package");
        testTourist.setCheckInDate("2024-01-15");
        testTourist.setCheckOutDate("2024-01-20");
        testTourist.setRoomNumber("101");
        testTourist.setTotalCost(5000.0);
        testTourist.setIsActive(true);
        testTourist.setBookingStatus("CONFIRMED");
        
        testTouristDTO = new TouristDTO();
        testTouristDTO.setFirstName("John");
        testTouristDTO.setLastName("Doe");
        testTouristDTO.setEmail("john.doe@example.com");
        testTouristDTO.setPhone("9876543210");
        testTouristDTO.setCountry("USA");
        testTouristDTO.setPassportNumber("US12345678");
        testTouristDTO.setTourPackage("Gold Package");
        testTouristDTO.setCheckInDate("2024-01-15");
        testTouristDTO.setCheckOutDate("2024-01-20");
        testTouristDTO.setRoomNumber("101");
        testTouristDTO.setTotalCost(5000.0);
    }
    
    @Test
    public void testRegisterTourist() {
        when(touristRepository.save(any(Tourist.class))).thenReturn(testTourist);
        
        Tourist result = touristService.registerTourist(testTouristDTO);
        
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("PENDING", result.getBookingStatus());
        verify(touristRepository, times(1)).save(any(Tourist.class));
    }
    
    @Test
    public void testGetTouristById() {
        when(touristRepository.findById(1L)).thenReturn(Optional.of(testTourist));
        
        Tourist result = touristService.getTouristById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("John", result.getFirstName());
    }
    
    @Test
    public void testGetTouristByIdNotFound() {
        when(touristRepository.findById(anyLong())).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            touristService.getTouristById(999L);
        });
    }
    
    @Test
    public void testUpdateTourist() {
        when(touristRepository.findById(1L)).thenReturn(Optional.of(testTourist));
        when(touristRepository.save(any(Tourist.class))).thenReturn(testTourist);
        
        Tourist result = touristService.updateTourist(1L, testTouristDTO);
        
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(touristRepository, times(1)).save(any(Tourist.class));
    }
    
    @Test
    public void testConfirmBooking() {
        testTourist.setBookingStatus("PENDING");
        when(touristRepository.findById(1L)).thenReturn(Optional.of(testTourist));
        when(touristRepository.save(any(Tourist.class))).thenReturn(testTourist);
        
        Tourist result = touristService.confirmBooking(1L);
        
        assertNotNull(result);
        verify(touristRepository, times(1)).save(any(Tourist.class));
    }
    
    @Test
    public void testCancelBooking() {
        when(touristRepository.findById(1L)).thenReturn(Optional.of(testTourist));
        when(touristRepository.save(any(Tourist.class))).thenReturn(testTourist);
        
        touristService.cancelBooking(1L);
        
        verify(touristRepository, times(1)).save(any(Tourist.class));
    }
    
    @Test
    public void testDeleteTouristPermanently() {
        when(touristRepository.existsById(1L)).thenReturn(true);
        
        touristService.deleteTouristPermanently(1L);
        
        verify(touristRepository, times(1)).deleteById(1L);
    }
    
    @Test
    public void testDeleteTouristPermanentlyNotFound() {
        when(touristRepository.existsById(anyLong())).thenReturn(false);
        
        assertThrows(ResourceNotFoundException.class, () -> {
            touristService.deleteTouristPermanently(999L);
        });
    }
    
    @Test
    public void testUpdateBookingStatus() {
        when(touristRepository.findById(1L)).thenReturn(Optional.of(testTourist));
        when(touristRepository.save(any(Tourist.class))).thenReturn(testTourist);
        
        Tourist result = touristService.updateBookingStatus(1L, "CONFIRMED");
        
        assertNotNull(result);
        verify(touristRepository, times(1)).save(any(Tourist.class));
    }
    
    @Test
    public void testUpdateBookingStatusInvalid() {
        when(touristRepository.findById(1L)).thenReturn(Optional.of(testTourist));
        
        assertThrows(IllegalArgumentException.class, () -> {
            touristService.updateBookingStatus(1L, "INVALID");
        });
    }
}
