package com.example.apartmentsforrent.web.converter;

import com.example.apartmentsforrent.persistence.entity.Owner;
import com.example.apartmentsforrent.web.dto.OwnerDto;

public class OwnerDtoConverter {
    public Owner convertToOwner(OwnerDto ownerDto) {
        return Owner.builder()
                .name(ownerDto.getName())
                .surname(ownerDto.getSurname())
                .email(ownerDto.getEmail())
                .phoneNumber(ownerDto.getPhoneNumber())
                .passwordHash(ownerDto.getPasswordHash())
                .build();
    }

    public OwnerDto convertToOwnerDto(Owner owner) {
        return new OwnerDto(owner.getName(), owner.getSurname(),
                owner.getEmail(), owner.getPhoneNumber(), owner.getPasswordHash());
    }
}
