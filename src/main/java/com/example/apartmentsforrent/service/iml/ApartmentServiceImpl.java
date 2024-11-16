package com.example.apartmentsforrent.service.iml;

import com.example.apartmentsforrent.persistence.dao.ApartmentDao;
import com.example.apartmentsforrent.persistence.dao.ApartmentDetailsDao;
import com.example.apartmentsforrent.persistence.model.Apartment;
import com.example.apartmentsforrent.persistence.model.ApartmentDescription;
import com.example.apartmentsforrent.persistence.model.ApartmentDetails;
import com.example.apartmentsforrent.persistence.model.Owner;
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

    private final ApartmentDao apartmentDao;
    private final ApartmentDetailsDao apartmentDetailsDao;
    private final ApartmentDescriptionDao apartmentDescriptionDao;
    private final OwnerDao ownerDao;

    public ApartmentServiceImpl(ApartmentDao apartmentDao, ApartmentDetailsDao apartmentDetailsDao,
                                ApartmentDescriptionDao apartmentDescriptionDao, OwnerDao ownerDao) {
        this.apartmentDao = apartmentDao;
        this.apartmentDetailsDao = apartmentDetailsDao;
        this.apartmentDescriptionDao = apartmentDescriptionDao;
        this.ownerDao = ownerDao;
    }

    @Transactional
    @Override
    public Apartment create(Apartment apartment) {
        ApartmentDetails details = apartmentDetailsDao.create(apartment.getApartmentDetails());
        apartment.setApartmentDetailsId(details.getId());

        ApartmentDescription description = apartmentDescriptionDao.create(apartment.getApartmentDescription());
        apartment.setApartmentDescriptionId(description.getId());

        Optional<Owner> optionalOwner = ownerDao.findByEmail(apartment.getOwner().getEmail());
        if (optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            apartment.setOwnerId(owner.getId());
            apartment.getOwner().setId(owner.getId());
            if (!owner.equals(apartment.getOwner())) {
                ownerDao.update(apartment.getOwner());
            }
        } else {
            Owner owner = ownerDao.create(apartment.getOwner());
            apartment.setOwnerId(owner.getId());
        }

        return apartmentDao.create(apartment);
    }

    @Transactional
    @Override
    public Apartment update(Apartment apartment) {
        if (apartmentDao.read(apartment.getId()).isEmpty()) {
            throw new IllegalArgumentException(String.format("Apartment with id %s does not exist", apartment.getId()));
        }

        Apartment populatedApartment = apartmentDao.read(apartment.getId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Apartment with id %s does not exist", apartment.getId())));

        apartment.setApartmentDetailsId(populatedApartment.getApartmentDetailsId());
        apartment.getApartmentDetails().setId(populatedApartment.getApartmentDetailsId());

        apartment.setApartmentDescriptionId(populatedApartment.getApartmentDescriptionId());
        apartment.getApartmentDescription().setId(populatedApartment.getApartmentDescriptionId());

        apartment.setOwnerId(populatedApartment.getOwnerId());
        apartment.getOwner().setId(populatedApartment.getOwnerId());

        apartmentDao.update(apartment);
        apartmentDetailsDao.update(apartment.getApartmentDetails());
        apartmentDescriptionDao.update(apartment.getApartmentDescription());
        ownerDao.update(apartment.getOwner());
        return apartment;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (apartmentDao.read(id).isEmpty()) {
            throw new IllegalArgumentException(String.format("Apartment with id %s does not exist", id));
        }
        apartmentDao.delete(id);
        apartmentDetailsDao.delete(id);
        apartmentDescriptionDao.delete(id);
    }

    @Transactional
    @Override
    public Optional<Apartment> findById(Long id) {
        Optional<Apartment> optionalApartment = apartmentDao.read(id);
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
        return apartmentDetailsDao.findAll().stream()
                .map(this::buildApartmentByDetails)
                .sorted(Comparator.comparing(Apartment::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Apartment> findAll(int page, int size) {
        return apartmentDetailsDao.findAll(page, size).stream()
                .map(this::buildApartmentByDetails)
                .sorted(Comparator.comparing(Apartment::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<Apartment> getAllWithFiltering(int page, int size, BigDecimal priceFrom, BigDecimal priceTo, Integer quantityOfRoomsFrom,
                                               Integer quantityOfRoomsTo, Float areaFrom, Float areaTo, Integer floorFrom,
                                               Integer floorTo, Year buildYearFrom, Year buildYearTo) {
        return apartmentDetailsDao.getAllWithFiltering(page, size, priceFrom, priceTo, quantityOfRoomsFrom, quantityOfRoomsTo, areaFrom,
                areaTo, floorFrom, floorTo, buildYearFrom, buildYearTo).stream()
                .map(this::buildApartmentByDetails)
                .sorted(Comparator.comparing(Apartment::getId))
                .collect(Collectors.toList());
    }

    private Apartment buildApartment(Apartment apartment) {
        ApartmentDetails detail = apartmentDetailsDao.read(apartment.getApartmentDetailsId())
                .orElseThrow(() -> new IllegalArgumentException("There is no such details"));
        return getApartment(apartment, detail);
    }

    private Apartment buildApartmentByDetails(ApartmentDetails detail) {
        Apartment apartment = apartmentDao.findByDetailsId(detail.getId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Apartment with id %s does not exist", detail.getId())));
        return getApartment(apartment, detail);
    }

    private Apartment getApartment(Apartment apartment, ApartmentDetails detail) {
        ApartmentDescription description = apartmentDescriptionDao.read(apartment.getApartmentDescriptionId())
                .orElseThrow(() -> new IllegalArgumentException("There is no such description"));
        Owner owner = ownerDao.read(apartment.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("There is no such owner"));

        apartment.setApartmentDetails(detail);
        apartment.setApartmentDescription(description);
        apartment.setOwner(owner);

        return apartment;
    }
}
