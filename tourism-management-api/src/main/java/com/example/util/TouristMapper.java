package com.example.util;

import com.example.dto.TouristDTO;
import com.example.entity.Tourist;
import java.util.ArrayList;
import java.util.List;

public class TouristMapper {
    
    public static TouristDTO mapToDTO(Tourist tourist) {
        if (tourist == null) {
            return null;
        }
        
        TouristDTO dto = new TouristDTO();
        dto.setFirstName(tourist.getFirstName());
        dto.setLastName(tourist.getLastName());
        dto.setEmail(tourist.getEmail());
        dto.setPhone(tourist.getPhone());
        dto.setCountry(tourist.getCountry());
        dto.setPassportNumber(tourist.getPassportNumber());
        dto.setTourPackage(tourist.getTourPackage());
        dto.setCheckInDate(tourist.getCheckInDate());
        dto.setCheckOutDate(tourist.getCheckOutDate());
        dto.setRoomNumber(tourist.getRoomNumber());
        dto.setTotalCost(tourist.getTotalCost());
        
        return dto;
    }
    
    public static Tourist mapToEntity(TouristDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Tourist tourist = new Tourist();
        tourist.setFirstName(dto.getFirstName());
        tourist.setLastName(dto.getLastName());
        tourist.setEmail(dto.getEmail());
        tourist.setPhone(dto.getPhone());
        tourist.setCountry(dto.getCountry());
        tourist.setPassportNumber(dto.getPassportNumber());
        tourist.setTourPackage(dto.getTourPackage());
        tourist.setCheckInDate(dto.getCheckInDate());
        tourist.setCheckOutDate(dto.getCheckOutDate());
        tourist.setRoomNumber(dto.getRoomNumber());
        tourist.setTotalCost(dto.getTotalCost());
        tourist.setIsActive(true);
        tourist.setBookingStatus("PENDING");
        
        return tourist;
    }
    
    public static List<TouristDTO> mapToDTOList(List<Tourist> tourists) {
        List<TouristDTO> dtoList = new ArrayList<>();
        for (Tourist tourist : tourists) {
            dtoList.add(mapToDTO(tourist));
        }
        return dtoList;
    }
    
    public static List<Tourist> mapToEntityList(List<TouristDTO> dtos) {
        List<Tourist> tourists = new ArrayList<>();
        for (TouristDTO dto : dtos) {
            tourists.add(mapToEntity(dto));
        }
        return tourists;
    }
}
