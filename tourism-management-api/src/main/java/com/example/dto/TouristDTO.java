package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TouristDTO {
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,13}$", message = "Phone number should be valid (10-13 digits)")
    private String phone;
    
    @NotBlank(message = "Country is required")
    private String country;
    
    @NotBlank(message = "Passport number is required")
    @Size(min = 5, max = 20, message = "Passport number should be between 5 and 20 characters")
    private String passportNumber;
    
    @NotBlank(message = "Tour package is required")
    private String tourPackage;
    
    @NotBlank(message = "Check-in date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Check-in date should be in yyyy-MM-dd format")
    private String checkInDate;
    
    @NotBlank(message = "Check-out date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Check-out date should be in yyyy-MM-dd format")
    private String checkOutDate;
    
    @NotBlank(message = "Room number is required")
    private String roomNumber;
    
    @NotNull(message = "Total cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total cost must be greater than 0")
    private Double totalCost;
}
