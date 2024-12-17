package com.example.apartmentsforrent.web.converter;

import com.example.apartmentsforrent.persistence.entity.Apartment;
import com.example.apartmentsforrent.persistence.entity.ApartmentDescription;
import com.example.apartmentsforrent.persistence.entity.ApartmentDetails;
import com.example.apartmentsforrent.persistence.entity.Owner;
import com.example.apartmentsforrent.web.dto.ApartmentDescriptionDto;
import com.example.apartmentsforrent.web.dto.ApartmentDetailsDto;
import com.example.apartmentsforrent.web.dto.ApartmentDto;
import com.example.apartmentsforrent.web.dto.OwnerDto;

public class ApartmentDtoConverter {
    public Apartment convertToApartment(ApartmentDto apartmentDto) {
        ApartmentDetailsDto apartmentDetailsDto = apartmentDto.getApartmentDetails();
        ApartmentDetails apartmentDetails = ApartmentDetails.builder()
                .address(apartmentDetailsDto.getAddress())
                .buildYear(apartmentDetailsDto.getBuildYear())
                .price(apartmentDetailsDto.getPrice())
                .area(apartmentDetailsDto.getArea())
                .floor(apartmentDetailsDto.getFloor())
                .quantityOfRooms(apartmentDetailsDto.getQuantityOfRooms())
                .build();

        ApartmentDescriptionDto apartmentDescriptionDto = apartmentDto.getApartmentDescription();
        ApartmentDescription apartmentDescription = ApartmentDescription.builder()
                .condition(apartmentDescriptionDto.getCondition())
                .buildingType(apartmentDescriptionDto.getBuildingType())
                .additionalInfo(apartmentDescriptionDto.getAdditionalInfo())
                .build();

        OwnerDto ownerDto = apartmentDto.getOwner();
        Owner owner = Owner.builder()
                .email(ownerDto.getEmail())
                .name(ownerDto.getName())
                .surname(ownerDto.getSurname())
                .phoneNumber(ownerDto.getPhoneNumber())
                .passwordHash(ownerDto.getPasswordHash())
                .build();

        return Apartment.builder()
                .apartmentDetails(apartmentDetails)
                .apartmentDescription(apartmentDescription)
                .owner(owner)
                .build();
    }
}
