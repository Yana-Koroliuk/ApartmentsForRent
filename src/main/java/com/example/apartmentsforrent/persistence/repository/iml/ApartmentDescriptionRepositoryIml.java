package com.example.apartmentsforrent.persistence.repository.iml;

import com.example.apartmentsforrent.persistence.model.ApartmentDescription;
import com.example.apartmentsforrent.persistence.repository.ApartmentDescriptionRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class ApartmentDescriptionRepositoryIml implements ApartmentDescriptionRepository {
    private final HashMap<Long, ApartmentDescription> databaseMap = new HashMap<>();
    private long index = 0;

    @Override
    public ApartmentDescription save(ApartmentDescription apartmentDescription) {
        if (apartmentDescription.getId() == null) {
            apartmentDescription.setId(index);
            databaseMap.put(index, apartmentDescription);
            index++;
        } else {
            databaseMap.put(apartmentDescription.getId(), apartmentDescription);
        }
        return apartmentDescription;
    }

    @Override
    public Optional<ApartmentDescription> findById(Long id) {
        ApartmentDescription entity = databaseMap.get(id);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public Iterable<ApartmentDescription> findAll() {
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
