package com.example.apartmentsforrent.persistence.model;

public class ApartmentDescription {
    private Long id;
    private String condition;
    private BuildingType buildingType;
    private String additionalInfo;

    public ApartmentDescription() {
    }

    private ApartmentDescription(Builder builder) {
        this.id = builder.id;
        this.condition = builder.condition;
        this.buildingType = builder.buildingType;
        this.additionalInfo = builder.additionalInfo;
    }

    public static class Builder {
        private Long id;
        private String condition;
        private BuildingType buildingType;
        private String additionalInfo;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder buildingType(BuildingType buildingType) {
            this.buildingType = buildingType;
            return this;
        }

        public Builder additionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
            return this;
        }

        public ApartmentDescription build() {
            return new ApartmentDescription(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
