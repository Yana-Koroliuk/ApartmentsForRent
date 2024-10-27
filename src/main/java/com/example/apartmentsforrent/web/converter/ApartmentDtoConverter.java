package com.example.apartmentsforrent.web.converter;

import com.example.apartmentsforrent.persistence.model.Apartment;
import com.example.apartmentsforrent.persistence.model.ApartmentDescription;
import com.example.apartmentsforrent.persistence.model.ApartmentDetails;
import com.example.apartmentsforrent.persistence.model.Owner;
import com.example.apartmentsforrent.web.dto.ApartmentDescriptionDto;
import com.example.apartmentsforrent.web.dto.ApartmentDetailsDto;
import com.example.apartmentsforrent.web.dto.ApartmentDto;
import com.example.apartmentsforrent.web.dto.OwnerDto;

public class ApartmentDtoConverter {
    public Apartment convertToApartment(ApartmentDto apartmentDto) {
        Apartment.Builder builder = new Apartment.Builder();
        ApartmentDetailsDto apartmentDetailsDto = apartmentDto.getApartmentDetails();
        builder.apartmentDetails(new ApartmentDetails.Builder()
                .address(apartmentDetailsDto.getAddress())
                .buildYear(apartmentDetailsDto.getBuildYear())
                .price(apartmentDetailsDto.getPrice())
                .area(apartmentDetailsDto.getArea())
                .floor(apartmentDetailsDto.getFloor())
                .quantityOfRooms(apartmentDetailsDto.getQuantityOfRooms())
                .build());

        ApartmentDescriptionDto apartmentDescriptionDto = apartmentDto.getApartmentDescription();
        builder.apartmentDescription(new ApartmentDescription.Builder()
                .condition(apartmentDescriptionDto.getCondition())
                .buildingType(apartmentDescriptionDto.getBuildingType())
                .additionalInfo(apartmentDescriptionDto.getAdditionalInfo())
                .build());

        OwnerDto ownerDto = apartmentDto.getOwner();
        builder.owner(new Owner.Builder()
                .email(ownerDto.getEmail())
                .name(ownerDto.getName())
                .surname(ownerDto.getSurname())
                .phoneNumber(ownerDto.getPhoneNumber())
                .build());

        return builder.build();
    }
}
