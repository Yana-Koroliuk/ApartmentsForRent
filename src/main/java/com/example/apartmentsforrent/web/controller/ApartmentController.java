package com.example.apartmentsforrent.web.controller;

import com.example.apartmentsforrent.persistence.model.*;
import com.example.apartmentsforrent.service.ApartmentService;
import com.example.apartmentsforrent.web.converter.ApartmentConverter;
import com.example.apartmentsforrent.web.converter.ApartmentDescriptionDtoConverter;
import com.example.apartmentsforrent.web.converter.ApartmentDetailsDtoConverter;
import com.example.apartmentsforrent.web.dto.ApartmentDescriptionDto;
import com.example.apartmentsforrent.web.dto.ApartmentDetailsDto;
import com.example.apartmentsforrent.web.dto.ApartmentDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apartment")
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final ApartmentConverter apartmentConverter;
    private final ApartmentDetailsDtoConverter apartmentDetailsDtoConverter;
    private final ApartmentDescriptionDtoConverter apartmentDescriptionDtoConverter;

    public ApartmentController(ApartmentService apartmentService, ApartmentConverter apartmentConverter,
                               ApartmentDetailsDtoConverter apartmentDetailsDtoConverter,
                               ApartmentDescriptionDtoConverter apartmentDescriptionDtoConverter) {
        this.apartmentService = apartmentService;
        this.apartmentConverter = apartmentConverter;
        this.apartmentDetailsDtoConverter = apartmentDetailsDtoConverter;
        this.apartmentDescriptionDtoConverter = apartmentDescriptionDtoConverter;
    }

    @GetMapping("/{id}")
    public String getApartmentPage(@PathVariable("id") Long id, Model model) {
        ApartmentDto apartmentDto = apartmentConverter.convertToApartmentDto(apartmentService.findById(id).orElseThrow());
        model.addAttribute("apartment", apartmentDto);
        return "apartment";
    }

    @GetMapping("/create")
    public String createApartment(Model model) {
        model.addAttribute("apartment_details", new ApartmentDetailsDto());
        model.addAttribute("apartment_description", new ApartmentDescriptionDto());
        model.addAttribute("building_types", BuildingType.values());
        return "create_apartment";
    }

    @PostMapping("/create")
    public String saveApartment(@ModelAttribute("apartmentDetailsDto") ApartmentDetailsDto apartmentDetailsDto, @ModelAttribute("apartmentDescriptionDto") ApartmentDescriptionDto apartmentDescriptionDto) {
        ApartmentDetails apartmentDetails = apartmentDetailsDtoConverter.convertToApartmentDetails(apartmentDetailsDto);
        ApartmentDescription apartmentDescription = apartmentDescriptionDtoConverter.convertToApartmentDescription(apartmentDescriptionDto);
        apartmentService.create(apartmentDetails, apartmentDescription, new Owner());
        return "redirect:/search";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteApartment(@PathVariable("id") Long id) {
        apartmentService.deleteById(id);
        return "redirect:/";
    }
}
