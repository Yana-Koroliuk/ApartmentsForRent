package com.example.apartmentsforrent.web.controller;

import com.example.apartmentsforrent.service.ApartmentService;
import com.example.apartmentsforrent.web.converter.ApartmentConverter;
import com.example.apartmentsforrent.web.converter.ApartmentDescriptionDtoConverter;
import com.example.apartmentsforrent.web.converter.ApartmentDetailsDtoConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apartment")
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final ApartmentConverter apartmentConverter;

    public ApartmentController(ApartmentService apartmentService, ApartmentConverter apartmentConverter,
                               ApartmentDetailsDtoConverter apartmentDetailsDtoConverter,
                               ApartmentDescriptionDtoConverter apartmentDescriptionDtoConverter) {
        this.apartmentService = apartmentService;
        this.apartmentConverter = apartmentConverter;
    }
}
