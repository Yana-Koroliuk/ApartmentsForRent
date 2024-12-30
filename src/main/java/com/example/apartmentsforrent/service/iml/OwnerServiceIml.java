package com.example.apartmentsforrent.service.iml;

import com.example.apartmentsforrent.persistence.entity.Owner;
import com.example.apartmentsforrent.persistence.repository.OwnerRepository;
import com.example.apartmentsforrent.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerServiceIml implements OwnerService {
    private final OwnerRepository ownerRepository;

    @Override
    public Owner createOwner(Owner owner) {
        if (ownerRepository.findByEmail(owner.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken");
        }
        return ownerRepository.save(owner);
    }

    @Override
    public void updateOwner(Owner owner) {
        Owner existingOwner = ownerRepository.findById(owner.getId()).orElseThrow(() ->
                new IllegalArgumentException(String.format("Owner with id %s does not exist", owner.getId())));

        existingOwner.setName(owner.getName());
        existingOwner.setSurname(owner.getSurname());
        existingOwner.setEmail(owner.getEmail());
        existingOwner.setPhoneNumber(owner.getPhoneNumber());
        existingOwner.setPasswordHash(owner.getPasswordHash());

        ownerRepository.save(existingOwner);
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
        if (ownerRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException(String.format("Owner with id %s does not exist", id));
        }
        ownerRepository.deleteById(id);
    }

    @Override
    public Boolean isEmailTaken(String email) {
        return ownerRepository.findByEmail(email).isPresent();
    }
}
