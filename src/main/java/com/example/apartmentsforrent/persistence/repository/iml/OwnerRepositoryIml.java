package com.example.apartmentsforrent.persistence.repository.iml;

import com.example.apartmentsforrent.persistence.model.Owner;
import com.example.apartmentsforrent.persistence.repository.OwnerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class OwnerRepositoryIml implements OwnerRepository {
    private final HashMap<Long, Owner> databaseMap = new HashMap<>();
    private long index = 0;

    @PostConstruct
    public void initializeDatabase() {
        Owner owner1 = new Owner.Builder()
                .name("Name1")
                .surname("Surname1")
                .email("owner1@gmail.com")
                .phoneNumber("+380239871345")
                .passwordHash("2frg3vb12e56")
                .build();

        Owner owner2 = new Owner.Builder()
                .name("Name2")
                .surname("Surname2")
                .email("owner2@gmail.com")
                .phoneNumber("+380239872222")
                .passwordHash("2fr3gvb11234")
                .build();

        save(owner1);
        save(owner2);
    }

    @Override
    public Optional<Owner> findByEmail(String email) {
        for (Owner owner : databaseMap.values()) {
            if (owner.getEmail().equals(email)) {
                return Optional.of(owner);
            }
        }
        return Optional.empty();
    }

    @Override
    public Owner save(Owner owner) {
        if (owner.getId() == null) {
            owner.setId(index);
            databaseMap.put(index, owner);
            index++;
        } else {
            databaseMap.put(owner.getId(), owner);
        }
        return owner;
    }

    @Override
    public Optional<Owner> findById(Long id) {
        Owner entity = databaseMap.get(id);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public Iterable<Owner> findAll() {
        return databaseMap.values();
    }

    @Override
    public boolean existsById(Long id) {
        return databaseMap.containsKey(id);
    }

    @Override
    public void deleteById(Long id) {
        databaseMap.remove(id);
    }
}
