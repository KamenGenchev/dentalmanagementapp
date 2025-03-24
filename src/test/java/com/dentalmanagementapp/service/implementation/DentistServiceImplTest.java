package com.dentalmanagementapp.service.implementation;

import com.dentalmanagementapp.dtos.auth.DentistRegisterDto;
import com.dentalmanagementapp.entities.Dentist;
import com.dentalmanagementapp.mappers.DentistMapper;
import com.dentalmanagementapp.repository.DentistRepository;
import com.dentalmanagementapp.service.DentistService;
import com.dentalmanagementapp.validation.DentistValidation;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import jakarta.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DentistServiceImplTest {
    @Autowired
    private Validator validator;

    @Mock
    private DentistRepository dentistRepository;

    @Mock
    private DentistMapper dentistMapper;

    @Mock
    private DentistValidation dentistValidation;

    @InjectMocks
    private DentistService dentistService;

    @Test
    public void registerDentist() {
        DentistRegisterDto dentistRegisterDto = new DentistRegisterDto("John", "Doe", "john@example.com", "password");
        Dentist dentist = new Dentist("John", "Doe", "john@example.com", "password");

        MockitoAnnotations.openMocks(this);

        when(dentistMapper.fromRegisteringDto(dentistRegisterDto)).thenReturn(dentist);

        dentistService.registerDentist(dentistRegisterDto);

        verify(dentistValidation, times(1)).validateEmailUniqueness(dentistRegisterDto.email());
        verify(dentistMapper, times(1)).fromRegisteringDto(dentistRegisterDto);
        verify(dentistRepository, times(1)).save(dentist);
    }

}