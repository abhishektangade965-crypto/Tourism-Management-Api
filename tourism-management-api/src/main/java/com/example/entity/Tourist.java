package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tourists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tourist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "phone", nullable = false)
    private String phone;
    
    @Column(name = "country")
    private String country;
    
    @Column(name = "passport_number")
    private String passportNumber;
    
    @Column(name = "tour_package")
    private String tourPackage;
    
    @Column(name = "check_in_date")
    private String checkInDate;
    
    @Column(name = "check_out_date")
    private String checkOutDate;
    
    @Column(name = "room_number")
    private String roomNumber;
    
    @Column(name = "total_cost")
    private Double totalCost;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "booking_status")
    private String bookingStatus; // CONFIRMED, PENDING, CANCELLED
}
