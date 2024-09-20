package com.example.apartmentsforrent.web.converter;

import com.example.apartmentsforrent.persistence.model.ApartmentDescription;
import com.example.apartmentsforrent.web.dto.ApartmentDescriptionDto;

public class ApartmentDescriptionDtoConverter {
    public ApartmentDescription convertToApartmentDescription(ApartmentDescriptionDto apartmentDescriptionDto) {
        return new ApartmentDescription.Builder()
                .condition(apartmentDescriptionDto.getCondition())
                .buildingType(apartmentDescriptionDto.getBuildingType())
                .additionalInfo(apartmentDescriptionDto.getAdditionalInfo())
                .build();
    }
}
