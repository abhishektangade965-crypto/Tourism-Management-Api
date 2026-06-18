package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourPackageDTO {
    
    @NotBlank(message = "Package name is required")
    @Size(min = 3, max = 100, message = "Package name must be between 3 and 100 characters")
    private String packageName;
    
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    private String description;
    
    @NotBlank(message = "Destination is required")
    @Size(min = 3, max = 50, message = "Destination must be between 3 and 50 characters")
    private String destination;
    
    @NotNull(message = "Price per person is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private Double pricePerPerson;
    
    @NotNull(message = "Duration (days) is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 365, message = "Duration cannot exceed 365 days")
    private Integer durationDays;
    
    @NotNull(message = "Maximum tourists capacity is required")
    @Min(value = 1, message = "Maximum tourists must be at least 1")
    private Integer maxTourists;
    
    private String includedMeals; // BREAKFAST, LUNCH, DINNER, ALL_MEALS
    
    private Boolean includedFlights = false;
    
    private Boolean includedAccommodation = false;
    
    @Pattern(regexp = "EASY|MODERATE|HARD", message = "Difficulty level must be EASY, MODERATE, or HARD")
    private String difficultyLevel;
    
    private String bestSeason;
}
