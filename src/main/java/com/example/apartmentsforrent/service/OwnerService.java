package com.example.apartmentsforrent.service;

import com.example.apartmentsforrent.persistence.entity.Owner;

import java.util.Optional;

public interface OwnerService {
    Owner createOwner(Owner owner);
    void updateOwner(Owner owner);
    Optional<Owner> getOwnerById(Long id);
    Optional<Owner> getOwnerByEmail(String email);
    void deleteOwner(Long id);
    Boolean isEmailTaken(String email);
}
