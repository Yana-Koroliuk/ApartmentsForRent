package com.example.apartmentsforrent.service;

import com.example.apartmentsforrent.persistence.model.Owner;

public interface OwnerService {
    Owner saveOwner(Owner owner);
    Owner getOwnerById(Long id);
    Owner getOwnerByEmail(String email);
    void deleteOwner(Long id);
    Boolean isEmailTaken(String email);
}
