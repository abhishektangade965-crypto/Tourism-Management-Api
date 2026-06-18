-- Create Database
CREATE DATABASE IF NOT EXISTS tourism_management_db;

-- Use Database
USE tourism_management_db;

-- Create Tourists Table
CREATE TABLE IF NOT EXISTS tourists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    country VARCHAR(50),
    passport_number VARCHAR(20),
    tour_package VARCHAR(100),
    check_in_date DATE,
    check_out_date DATE,
    room_number VARCHAR(10),
    total_cost DECIMAL(10, 2),
    is_active BOOLEAN DEFAULT TRUE,
    booking_status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_email (email),
    INDEX idx_country (country),
    INDEX idx_tour_package (tour_package),
    INDEX idx_booking_status (booking_status),
    INDEX idx_is_active (is_active),
    INDEX idx_check_in_date (check_in_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create User Roles Join Table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert Sample Data
INSERT INTO tourists (first_name, last_name, email, phone, country, passport_number, tour_package, check_in_date, check_out_date, room_number, total_cost, is_active, booking_status) 
VALUES 
    -- user@example.com (The primary test login user)
    ('Alex', 'Rivera', 'user@example.com', '9876543210', 'USA', 'US12345678', 'Maldive Serenity Escape', DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '101', 2499.00, TRUE, 'CONFIRMED'),
    ('Alex', 'Rivera', 'user@example.com', '9876543210', 'USA', 'US12345678', 'Alpine Summit Trek', DATE_SUB(CURRENT_DATE, INTERVAL 20 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 15 DAY), '201', 3200.00, FALSE, 'EXPIRED'),
    ('Alex', 'Rivera', 'user@example.com', '9876543210', 'USA', 'US12345678', 'Venetian Romance', DATE_ADD(CURRENT_DATE, INTERVAL 15 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 20 DAY), '301', 2100.00, FALSE, 'CANCELLED'),
    ('Alex', 'Rivera', 'user@example.com', '9876543210', 'USA', 'US12345678', 'Kyoto Cultural Zen', DATE_SUB(CURRENT_DATE, INTERVAL 10 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 5 DAY), '401', 1850.00, FALSE, 'COMPLETED'),
    ('Alex', 'Rivera', 'user@example.com', '9876543210', 'USA', 'US12345678', 'Egyptian Pyramids Expedition', DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY), '102', 1599.00, TRUE, 'PENDING'),
    
    -- Other Customers across specified countries
    ('Rohan', 'Gupta', 'rohan.gupta@example.com', '9876543211', 'India', 'IN98765432', 'Maldive Serenity Escape', DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 8 DAY), '202', 2499.00, TRUE, 'CONFIRMED'),
    ('Carlos', 'Sainz', 'carlos@example.com', '9876543212', 'Spain', 'ES11111111', 'Greek Islands Sailing Odyssey', DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 22 DAY), '302', 2850.00, FALSE, 'COMPLETED'),
    ('Emily', 'Chen', 'emily@example.com', '9876543213', 'Canada', 'CA22222222', 'Amazon Rainforest Eco Adventure', DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 6 DAY), '402', 1950.00, FALSE, 'CANCELLED'),
    ('James', 'Smith', 'james@example.com', '9876543214', 'UK', 'GB33333333', 'Alpine Summit Trek', DATE_ADD(CURRENT_DATE, INTERVAL 12 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 19 DAY), '501', 3200.00, TRUE, 'PENDING'),
    ('Sophia', 'Müller', 'sophia@example.com', '9876543215', 'Germany', 'DE55555555', 'Kyoto Cultural Zen', DATE_SUB(CURRENT_DATE, INTERVAL 15 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 11 DAY), '103', 1850.00, FALSE, 'EXPIRED'),
    ('Pierre', 'Dubois', 'pierre@example.com', '9876543216', 'France', 'FR66666666', 'Venetian Romance', DATE_SUB(CURRENT_DATE, INTERVAL 8 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 2 DAY), '203', 2100.00, FALSE, 'COMPLETED'),
    ('Lee', 'Min', 'lee@example.com', '9876543217', 'Singapore', 'SG77777777', 'Egyptian Pyramids Expedition', DATE_ADD(CURRENT_DATE, INTERVAL 4 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '303', 1599.00, TRUE, 'CONFIRMED'),
    ('Jack', 'Miller', 'jack@example.com', '9876543218', 'Australia', 'AU88888888', 'Greek Islands Sailing Odyssey', DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), '403', 2850.00, FALSE, 'CANCELLED'),
    ('Sakura', 'Tanaka', 'sakura@example.com', '9876543219', 'Japan', 'JP99999999', 'Amazon Rainforest Eco Adventure', DATE_ADD(CURRENT_DATE, INTERVAL 15 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 20 DAY), '502', 1950.00, TRUE, 'PENDING'),
    ('Michael', 'Brown', 'michael@example.com', '9876543220', 'USA', 'US44444444', 'Maldive Serenity Escape', DATE_SUB(CURRENT_DATE, INTERVAL 25 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 20 DAY), '104', 2499.00, FALSE, 'EXPIRED'),
    ('Lucas', 'Garcia', 'lucas@example.com', '9876543221', 'Spain', 'ES22222222', 'Alpine Summit Trek', DATE_ADD(CURRENT_DATE, INTERVAL 8 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 15 DAY), '204', 3200.00, TRUE, 'CONFIRMED'),
    ('Kenji', 'Sato', 'kenji@example.com', '9876543222', 'Japan', 'JP11111111', 'Venetian Romance', DATE_ADD(CURRENT_DATE, INTERVAL 6 DAY), DATE_ADD(CURRENT_DATE, INTERVAL 12 DAY), '304', 2100.00, TRUE, 'CONFIRMED'),
    ('Sarah', 'Connor', 'sarah@example.com', '9876543223', 'Canada', 'CA33333333', 'Egyptian Pyramids Expedition', DATE_SUB(CURRENT_DATE, INTERVAL 18 DAY), DATE_SUB(CURRENT_DATE, INTERVAL 12 DAY), '404', 1599.00, FALSE, 'EXPIRED');

-- Insert Sample Users (Passwords are BCrypt hashed)
-- admin / admin123
INSERT INTO users (id, username, email, password) 
VALUES (1, 'admin', 'admin@example.com', '$2a$10$R7M4.6Nl0kS3c0vj/pTFOuB5.r7F1npeEwB6WnS7x/m.Cdf7r6Jle');

-- user / user123
INSERT INTO users (id, username, email, password) 
VALUES (2, 'user', 'user@example.com', '$2a$10$iIpx7b1zN92Vp/H3Llh9L.7j/F7tZl35xN45l2Kx791l6gLqj1.u.');

-- Assign Roles
INSERT INTO user_roles (user_id, role) 
VALUES 
    (1, 'ROLE_ADMIN'),
    (1, 'ROLE_USER'),
    (2, 'ROLE_USER');

-- Verify Data
SELECT * FROM tourists;

-- View Statistics
SELECT 
    COUNT(*) as total_tourists,
    SUM(CASE WHEN is_active = TRUE THEN 1 ELSE 0 END) as active_tourists,
    SUM(CASE WHEN is_active = FALSE THEN 1 ELSE 0 END) as inactive_tourists,
    COUNT(DISTINCT country) as total_countries,
    COUNT(DISTINCT tour_package) as total_packages,
    AVG(total_cost) as average_cost,
    MAX(total_cost) as max_cost,
    MIN(total_cost) as min_cost
FROM tourists;

-- Country-wise Statistics
SELECT 
    country,
    COUNT(*) as tourist_count,
    SUM(CASE WHEN booking_status = 'CONFIRMED' THEN 1 ELSE 0 END) as confirmed_bookings,
    SUM(CASE WHEN booking_status = 'PENDING' THEN 1 ELSE 0 END) as pending_bookings,
    SUM(CASE WHEN booking_status = 'CANCELLED' THEN 1 ELSE 0 END) as cancelled_bookings,
    AVG(total_cost) as avg_cost
FROM tourists
GROUP BY country
ORDER BY tourist_count DESC;

-- Tour Package Statistics
SELECT 
    tour_package,
    COUNT(*) as package_count,
    AVG(total_cost) as avg_package_cost,
    MAX(total_cost) as max_package_cost,
    MIN(total_cost) as min_package_cost
FROM tourists
GROUP BY tour_package
ORDER BY package_count DESC;

-- Booking Status Statistics
SELECT 
    booking_status,
    COUNT(*) as status_count,
    SUM(total_cost) as total_revenue
FROM tourists
GROUP BY booking_status;
