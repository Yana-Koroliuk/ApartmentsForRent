package com.example.apartmentsforrent.web.converter;

import com.example.apartmentsforrent.persistence.entity.ApartmentDescription;
import com.example.apartmentsforrent.web.dto.ApartmentDescriptionDto;

public class ApartmentDescriptionDtoConverter {
    public ApartmentDescription convertToApartmentDescription(ApartmentDescriptionDto apartmentDescriptionDto) {
        return ApartmentDescription.builder()
                .condition(apartmentDescriptionDto.getCondition())
                .buildingType(apartmentDescriptionDto.getBuildingType())
                .additionalInfo(apartmentDescriptionDto.getAdditionalInfo())
                .build();
    }
}
