package com.example.apartmentsforrent.service.iml;

import com.example.apartmentsforrent.persistence.model.Owner;
import com.example.apartmentsforrent.persistence.repository.OwnerRepository;
import com.example.apartmentsforrent.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerServiceIml implements OwnerService {
    OwnerRepository ownerRepository;

    @Autowired
    public void setOwnerRepository(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner saveOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    @Override
    public Optional<Owner> getOwnerByEmail(String email) {
        return ownerRepository.findByEmail(email);
    }

    @Override
    public void deleteOwner(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new IllegalArgumentException(String.format("Owner with id %s does not exist", id));
        }
        ownerRepository.deleteById(id);
    }

    @Override
    public Boolean isEmailTaken(String email) {
        return ownerRepository.findByEmail(email).isPresent();
    }
}
