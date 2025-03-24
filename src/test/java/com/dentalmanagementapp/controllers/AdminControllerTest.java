package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.dentist.DentistDetailsDto;
import com.dentalmanagementapp.service.DentistService;
import com.dentalmanagementapp.dtos.dentist.DentistDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;


@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DentistService dentistService;

    @Test
    void getDentistById_returnsDentistDetailsDto() throws Exception {
        DentistDto dentistDto = new DentistDto("john.doe@example.com", "Pesho", "Gorski");
        DentistDetailsDto dentistDetailsDto = new DentistDetailsDto(dentistDto, new ArrayList<>());

        when(dentistService.getDentistById(anyLong())).thenReturn(dentistDetailsDto);

        mockMvc.perform(get("/api/admins/dentists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dentistDetailsDto)));
    }

    @Test
    void getDentistById_returnsNotFound() throws Exception {
        when(dentistService.getDentistById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/admins/dentists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}