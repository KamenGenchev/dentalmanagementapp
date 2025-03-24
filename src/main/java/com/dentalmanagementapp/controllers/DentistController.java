package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.dentist.DentistDetailsDto;
import com.dentalmanagementapp.dtos.dentist.DentistDto;
import com.dentalmanagementapp.service.DentistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dentalmanagementapp.security.UserContext.getCurrentUser;

@RestController
@RequestMapping("/api/dentists")
public class DentistController {
    private final DentistService dentistService;

    @Autowired
    public DentistController(DentistService dentistService) {
        this.dentistService = dentistService;
    }

    @GetMapping()
    public ResponseEntity<DentistDetailsDto> getProfile() {
        DentistDetailsDto dentist = dentistService.getDentistById(getCurrentUser().currentUserId());
        return new ResponseEntity<>(dentist, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Void> updateSelf(@Valid @RequestBody DentistDto dentistDto) {
        dentistService.updateDentist(getCurrentUser().currentUserId(), dentistDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}