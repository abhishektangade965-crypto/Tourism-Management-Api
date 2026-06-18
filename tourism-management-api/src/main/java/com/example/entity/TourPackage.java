package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tour_packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourPackage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "package_name", nullable = false, unique = true)
    private String packageName;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "destination", nullable = false)
    private String destination;
    
    @Column(name = "price_per_person")
    private Double pricePerPerson;
    
    @Column(name = "duration_days")
    private Integer durationDays;
    
    @Column(name = "max_tourists")
    private Integer maxTourists;
    
    @Column(name = "included_meals")
    private String includedMeals; // BREAKFAST, LUNCH, DINNER, ALL_MEALS
    
    @Column(name = "included_flights")
    private Boolean includedFlights = false;
    
    @Column(name = "included_accommodation")
    private Boolean includedAccommodation = false;
    
    @Column(name = "rating")
    private Double rating = 0.0;
    
    @Column(name = "number_of_reviews")
    private Long numberOfReviews = 0L;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "difficulty_level") // EASY, MODERATE, HARD
    private String difficultyLevel;
    
    @Column(name = "best_season")
    private String bestSeason;
}
