package com.example.apartmentsforrent.persistence.repository.iml;

import com.example.apartmentsforrent.persistence.model.ApartmentDetails;
import com.example.apartmentsforrent.persistence.repository.ApartmentDetailsRepository;

import java.util.HashMap;
import java.util.Optional;

public class ApartmentDetailsRepositoryIml implements ApartmentDetailsRepository {
    private final HashMap<Long, ApartmentDetails> databaseMap = new HashMap<>();
    private long index = 0;

    @Override
    public ApartmentDetails save(ApartmentDetails apartmentDetails) {
        if (apartmentDetails.getId() == null) {
            apartmentDetails.setId(index);
            databaseMap.put(index, apartmentDetails);
            index++;
        } else {
            databaseMap.put(apartmentDetails.getId(), apartmentDetails);
        }
        return apartmentDetails;
    }

    @Override
    public Optional<ApartmentDetails> findById(Long id) {
        ApartmentDetails entity = databaseMap.get(id);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public Iterable<ApartmentDetails> findAll() {
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
