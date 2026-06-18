package com.example.repository;

import com.example.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourPackageRepository extends JpaRepository<TourPackage, Long> {
    
    Optional<TourPackage> findByPackageName(String packageName);
    
    List<TourPackage> findByDestination(String destination);
    
    List<TourPackage> findByIsActiveTrue();
    
    List<TourPackage> findByIsActiveFalse();
    
    @Query("SELECT tp FROM TourPackage tp WHERE tp.pricePerPerson <= :maxPrice ORDER BY tp.rating DESC")
    List<TourPackage> findPackagesByMaxPrice(@Param("maxPrice") Double maxPrice);
    
    @Query("SELECT tp FROM TourPackage tp WHERE tp.durationDays BETWEEN :minDays AND :maxDays")
    List<TourPackage> findPackagesByDuration(@Param("minDays") Integer minDays, @Param("maxDays") Integer maxDays);
    
    @Query("SELECT tp FROM TourPackage tp WHERE tp.rating >= :minRating ORDER BY tp.rating DESC")
    List<TourPackage> findTopRatedPackages(@Param("minRating") Double minRating);
    
    @Query("SELECT tp FROM TourPackage tp WHERE tp.difficultyLevel = :level AND tp.isActive = true")
    List<TourPackage> findPackagesByDifficultyLevel(@Param("level") String level);
    
    @Query("SELECT tp FROM TourPackage tp WHERE tp.includedFlights = true")
    List<TourPackage> findPackagesWithFlights();
    
    @Query("SELECT tp FROM TourPackage tp WHERE tp.includedAccommodation = true")
    List<TourPackage> findPackagesWithAccommodation();
    
    Long countByDestination(String destination);
    
    Long countByDifficultyLevel(String difficultyLevel);
    
    @Query("SELECT COUNT(tp) FROM TourPackage tp WHERE tp.isActive = true")
    Long countActivePackages();
}
