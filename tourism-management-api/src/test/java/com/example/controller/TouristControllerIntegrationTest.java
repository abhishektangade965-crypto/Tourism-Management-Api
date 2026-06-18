package com.example.controller;

import com.example.entity.Tourist;
import com.example.dto.TouristDTO;
import com.example.repository.TouristRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
public class TouristControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private TouristRepository touristRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private Tourist testTourist;
    
    @BeforeEach
    public void setUp() {
        touristRepository.deleteAll();
        
        testTourist = new Tourist();
        testTourist.setFirstName("Jane");
        testTourist.setLastName("Smith");
        testTourist.setEmail("jane.smith@example.com");
        testTourist.setPhone("9876543210");
        testTourist.setCountry("India");
        testTourist.setPassportNumber("IN98765432");
        testTourist.setTourPackage("Silver Package");
        testTourist.setCheckInDate("2024-02-10");
        testTourist.setCheckOutDate("2024-02-15");
        testTourist.setRoomNumber("201");
        testTourist.setTotalCost(3500.0);
        testTourist.setIsActive(true);
        testTourist.setBookingStatus("PENDING");
        
        testTourist = touristRepository.save(testTourist);
    }
    
    @Test
    public void testRegisterTourist() throws Exception {
        TouristDTO newTourist = new TouristDTO();
        newTourist.setFirstName("Bob");
        newTourist.setLastName("Johnson");
        newTourist.setEmail("bob.johnson@example.com");
        newTourist.setPhone("9876543211");
        newTourist.setCountry("UK");
        newTourist.setPassportNumber("UK11111111");
        newTourist.setTourPackage("Platinum Package");
        newTourist.setCheckInDate("2024-03-01");
        newTourist.setCheckOutDate("2024-03-10");
        newTourist.setRoomNumber("301");
        newTourist.setTotalCost(8000.0);
        
        mockMvc.perform(post("/api/tourists/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTourist)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
    
    @Test
    public void testGetAllTourists() throws Exception {
        mockMvc.perform(get("/api/tourists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
    
    @Test
    public void testGetTouristById() throws Exception {
        mockMvc.perform(get("/api/tourists/" + testTourist.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.firstName", is("Jane")))
                .andDo(print());
    }
    
    @Test
    public void testUpdateTourist() throws Exception {
        TouristDTO updateDTO = new TouristDTO();
        updateDTO.setFirstName("Jane");
        updateDTO.setLastName("Doe");
        updateDTO.setEmail("jane.doe@example.com");
        updateDTO.setPhone("9876543210");
        updateDTO.setCountry("Canada");
        updateDTO.setPassportNumber("CA98765432");
        updateDTO.setTourPackage("Gold Package");
        updateDTO.setCheckInDate("2024-02-10");
        updateDTO.setCheckOutDate("2024-02-15");
        updateDTO.setRoomNumber("201");
        updateDTO.setTotalCost(4500.0);
        
        mockMvc.perform(put("/api/tourists/" + testTourist.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
    
    @Test
    public void testGetTouristsByCountry() throws Exception {
        mockMvc.perform(get("/api/tourists/country/" + testTourist.getCountry())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
    
    @Test
    public void testGetTouristsByPackage() throws Exception {
        mockMvc.perform(get("/api/tourists/package/" + testTourist.getTourPackage())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
    
    @Test
    public void testGetActiveTourists() throws Exception {
        mockMvc.perform(get("/api/tourists/active/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
    
    @Test
    public void testConfirmBooking() throws Exception {
        mockMvc.perform(put("/api/tourists/" + testTourist.getId() + "/confirm")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
    
    @Test
    public void testCancelBooking() throws Exception {
        mockMvc.perform(put("/api/tourists/" + testTourist.getId() + "/cancel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
    
    @Test
    public void testDeleteTourist() throws Exception {
        mockMvc.perform(delete("/api/tourists/" + testTourist.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
    
    @Test
    public void testCountActiveTourists() throws Exception {
        mockMvc.perform(get("/api/tourists/count/active")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", is(true)))
                .andDo(print());
    }
}
