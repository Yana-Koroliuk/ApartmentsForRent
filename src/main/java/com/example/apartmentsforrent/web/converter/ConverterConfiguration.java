package com.example.apartmentsforrent.web.converter;


import com.example.apartmentsforrent.persistence.model.Apartment;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class ConverterConfiguration {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ApartmentDtoConverter apartmentDtoConverter() {
        return new ApartmentDtoConverter();
    }

    @Bean
    public ApartmentConverter apartmentConverter() {
        return new ApartmentConverter();
    }
}
