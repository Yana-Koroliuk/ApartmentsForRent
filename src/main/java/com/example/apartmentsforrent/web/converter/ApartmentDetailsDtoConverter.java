package com.example.apartmentsforrent.web.converter;

import com.example.apartmentsforrent.persistence.model.ApartmentDetails;
import com.example.apartmentsforrent.web.dto.ApartmentDetailsDto;

public class ApartmentDetailsDtoConverter {
    public ApartmentDetails convertToApartmentDetails(ApartmentDetailsDto apartmentDetailsDto) {
        return new ApartmentDetails.Builder()
                .address(apartmentDetailsDto.getAddress())
                .buildYear(apartmentDetailsDto.getBuildYear())
                .price(apartmentDetailsDto.getPrice())
                .area(apartmentDetailsDto.getArea())
                .floor(apartmentDetailsDto.getFloor())
                .quantityOfRooms(apartmentDetailsDto.getQuantityOfRooms())
                .build();
    }
}
