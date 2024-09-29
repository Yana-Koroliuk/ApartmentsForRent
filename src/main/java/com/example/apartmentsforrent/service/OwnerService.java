package com.example.apartmentsforrent.service;

import com.example.apartmentsforrent.persistence.model.Owner;

import java.util.Optional;

public interface OwnerService {
    Owner saveOwner(Owner owner);
    Optional<Owner> getOwnerById(Long id);
    Optional<Owner> getOwnerByEmail(String email);
    void deleteOwner(Long id);
    Boolean isEmailTaken(String email);
}
