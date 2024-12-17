package com.example.apartmentsforrent.persistence.repository;

import com.example.apartmentsforrent.persistence.entity.Apartment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApartmentRepository extends CrudRepository<Apartment, Long> {

    Optional<Apartment> findByDetailsId(Long detailsId);

    Optional<Apartment> findByDescriptionId(Long descriptionId);
}
