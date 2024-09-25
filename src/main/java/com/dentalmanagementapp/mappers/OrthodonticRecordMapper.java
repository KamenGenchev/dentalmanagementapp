package com.dentalmanagementapp.mappers;

import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import com.dentalmanagementapp.dtos.record.OrthodonticRecordDto;
import com.dentalmanagementapp.dtos.record.OrthodonticRecordUpdateDto;
import com.dentalmanagementapp.entities.DentistPatient;
import com.dentalmanagementapp.entities.OrthodonticRecord;
import com.dentalmanagementapp.service.DentistPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrthodonticRecordMapper {
    private final DentistPatientService dentistPatientService;

    @Autowired
    public OrthodonticRecordMapper(DentistPatientService dentistPatientService) {
        this.dentistPatientService = dentistPatientService;
    }

    public OrthodonticRecordDto toDto(OrthodonticRecord entity) {
        PatientForDentistDto patientDto = new PatientForDentistDto(
                entity.getDentistPatient().getPatient().getFirstName(),
                entity.getDentistPatient().getPatient().getLastName(),
                entity.getDentistPatient().getLocalId()
        );

        return new OrthodonticRecordDto(patientDto, entity.getDescription(), entity.getRecordDate());
    }

    public OrthodonticRecord toEntity(OrthodonticRecordDto dto) {
        DentistPatient dentistPatient = dentistPatientService.getDentistPatientByPatientLocalId(dto.patientDto().localId());

        return new OrthodonticRecord(dentistPatient, dto.description());
    }

    public OrthodonticRecord toEntity(OrthodonticRecordUpdateDto recordUpdateDto, OrthodonticRecord record) {
        record.setDescription(recordUpdateDto.description());
        return record;
    }
}
