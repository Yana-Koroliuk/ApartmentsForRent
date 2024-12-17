package com.example.apartmentsforrent.service.iml;

import com.example.apartmentsforrent.persistence.entity.*;
import com.example.apartmentsforrent.persistence.repository.ApartmentDescriptionRepository;
import com.example.apartmentsforrent.persistence.repository.ApartmentDetailsRepository;
import com.example.apartmentsforrent.persistence.repository.ApartmentRepository;
import com.example.apartmentsforrent.persistence.repository.OwnerRepository;
import com.example.apartmentsforrent.service.ApartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final ApartmentDetailsRepository apartmentDetailsRepository;
    private final ApartmentDescriptionRepository apartmentDescriptionRepository;
    private final OwnerRepository ownerRepository;

    public ApartmentServiceImpl(ApartmentDetailsRepository apartmentDetailsRepository, ApartmentRepository apartmentRepository, OwnerRepository ownerRepository, ApartmentDescriptionRepository apartmentDescriptionRepository) {
        this.apartmentDetailsRepository = apartmentDetailsRepository;
        this.apartmentRepository = apartmentRepository;
        this.ownerRepository = ownerRepository;
        this.apartmentDescriptionRepository = apartmentDescriptionRepository;
    }

    @Transactional
    @Override
    public Apartment create(Apartment apartment) {
        ApartmentDetails details = apartmentDetailsRepository.save(apartment.getApartmentDetails());
        apartment.setApartmentDetails(details);

        ApartmentDescription description = apartmentDescriptionRepository.save(apartment.getApartmentDescription());
        apartment.setApartmentDescription(description);

        Optional<Owner> optionalOwner = ownerRepository.findByEmail(apartment.getOwner().getEmail());
        if (optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            apartment.setOwner(owner);
        } else {
            Owner owner = ownerRepository.save(apartment.getOwner());
            apartment.setOwner(owner);
        }

        return apartmentRepository.save(apartment);
    }

    @Transactional
    @Override
    public Apartment update(Apartment apartment) {
        Apartment existingApartment = apartmentRepository.findById(apartment.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Apartment with id %s does not exist", apartment.getId()))
                );

        ApartmentDetails details = apartment.getApartmentDetails();
        details.setId(existingApartment.getApartmentDetails().getId());
        ApartmentDetails updatedDetails = apartmentDetailsRepository.save(details);

        ApartmentDescription description = apartment.getApartmentDescription();
        description.setId(existingApartment.getApartmentDescription().getId());
        ApartmentDescription updatedDescription = apartmentDescriptionRepository.save(description);

        Owner owner = apartment.getOwner();
        owner.setId(existingApartment.getOwner().getId());
        Owner updatedOwner = ownerRepository.save(owner);

        existingApartment.setApartmentDetails(updatedDetails);
        existingApartment.setApartmentDescription(updatedDescription);
        existingApartment.setOwner(updatedOwner);

        return apartmentRepository.save(existingApartment);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Apartment apartment = apartmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Apartment with id %s does not exist", id)));
        apartmentRepository.deleteById(id);
        apartmentDetailsRepository.deleteById(apartment.getApartmentDetails().getId());
        apartmentDescriptionRepository.deleteById(apartment.getApartmentDescription().getId());
    }

    @Transactional
    @Override
    public Optional<Apartment> findById(Long id) {
        Optional<Apartment> optionalApartment = apartmentRepository.findById(id);
        if (optionalApartment.isPresent()) {
            Apartment apartment = buildApartment(optionalApartment.get());
            return Optional.of(apartment);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public List<Apartment> findAll() {
        return apartmentDetailsRepository.findAll().stream()
                .map(this::buildApartmentByDetails)
                .sorted(Comparator.comparing(Apartment::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Apartment> findAll(int page, int size) {
        int toIgnore = (page - 1) * size;
        return apartmentDetailsRepository.getAll(toIgnore, size).stream()
                .map(this::buildApartmentByDetails)
                .sorted(Comparator.comparing(Apartment::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Apartment> getAllWithFiltering(int page, int size, BigDecimal priceFrom, BigDecimal priceTo, Integer quantityOfRoomsFrom,
                                               Integer quantityOfRoomsTo, Float areaFrom, Float areaTo, Integer floorFrom,
                                               Integer floorTo, Year buildYearFrom, Year buildYearTo) {
        return apartmentDetailsRepository.getAllWithFiltering(page, size, priceFrom, priceTo, quantityOfRoomsFrom, quantityOfRoomsTo, areaFrom,
                        areaTo, floorFrom, floorTo, buildYearFrom != null ? buildYearFrom.getValue() : null,
                        buildYearTo != null ? buildYearTo.getValue() : null).stream()
                .map(this::buildApartmentByDetails)
                .sorted(Comparator.comparing(Apartment::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Apartment> findByBuildingType(BuildingType buildingType) {
        List<ApartmentDescription> descriptions = apartmentDescriptionRepository.findByBuildingType(buildingType);

        return descriptions.stream().map(description -> {
            Apartment apartment = apartmentRepository.findByDescriptionId(description.getId())
                    .orElseThrow(() -> new IllegalArgumentException("No apartment found for description id " + description.getId()));

            ApartmentDetails details = apartmentDetailsRepository.findById(apartment.getApartmentDetails().getId())
                    .orElseThrow(() -> new IllegalArgumentException("No details found for id " + apartment.getApartmentDetails().getId()));

            Owner owner = ownerRepository.findById(apartment.getOwner().getId())
                    .orElseThrow(() -> new IllegalArgumentException("No owner found for id " + apartment.getOwner().getId()));

            apartment.setApartmentDescription(description);
            apartment.setApartmentDetails(details);
            apartment.setOwner(owner);

            return apartment;
        }).collect(Collectors.toList());
    }

    private Apartment buildApartment(Apartment apartment) {
        ApartmentDetails detail = apartmentDetailsRepository.findById(apartment.getApartmentDetails().getId())
                .orElseThrow(() -> new IllegalArgumentException("There is no such details"));
        return getApartment(apartment, detail);
    }

    private Apartment buildApartmentByDetails(ApartmentDetails details) {
        Apartment apartment = apartmentRepository.findByDetailsId(details.getId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Apartment with id %s does not exist", details.getId())));
        return getApartment(apartment, details);
    }

    private Apartment getApartment(Apartment apartment, ApartmentDetails details) {
        ApartmentDescription description = apartmentDescriptionRepository.findById(apartment.getApartmentDescription().getId())
                .orElseThrow(() -> new IllegalArgumentException("Description not found for id " + apartment.getApartmentDescription().getId()));
        Owner owner = ownerRepository.findById(apartment.getOwner().getId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found for id " + apartment.getOwner().getId()));

        apartment.setApartmentDetails(details);
        apartment.setApartmentDescription(description);
        apartment.setOwner(owner);

        return apartment;
    }
}
