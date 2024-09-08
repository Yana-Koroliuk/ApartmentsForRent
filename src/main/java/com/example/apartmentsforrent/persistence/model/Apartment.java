package com.example.apartmentsforrent.persistence.model;

public class Apartment {
    private Long id;
    private ApartmentDetails apartmentDetails;
    private ApartmentDescription apartmentDescription;
    private Owner owner;

    public Apartment() {
    }

    private Apartment(Builder builder) {
        this.id = builder.id;
        this.apartmentDetails = builder.apartmentDetails;
        this.apartmentDescription = builder.apartmentDescription;
        this.owner = builder.owner;
    }

    public static class Builder {
        private Long id;
        private ApartmentDetails apartmentDetails;
        private ApartmentDescription apartmentDescription;
        private Owner owner;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder apartmentDetails(ApartmentDetails apartmentDetails) {
            this.apartmentDetails = apartmentDetails;
            return this;
        }

        public Builder apartmentDescription(ApartmentDescription apartmentDescription) {
            this.apartmentDescription = apartmentDescription;
            return this;
        }

        public Builder owner(Owner owner) {
            this.owner = owner;
            return this;
        }

        public Apartment build() {
            return new Apartment(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApartmentDetails getApartmentDetails() {
        return apartmentDetails;
    }

    public void setApartmentDetails(ApartmentDetails apartmentDetails) {
        this.apartmentDetails = apartmentDetails;
    }

    public ApartmentDescription getApartmentDescription() {
        return apartmentDescription;
    }

    public void setApartmentDescription(ApartmentDescription apartmentDescription) {
        this.apartmentDescription = apartmentDescription;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
