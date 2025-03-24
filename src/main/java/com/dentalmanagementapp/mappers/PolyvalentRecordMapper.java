package com.dentalmanagementapp.mappers;

import com.dentalmanagementapp.dtos.PolyvalentRecordUpdateDto;
import com.dentalmanagementapp.dtos.dentistpatient.PatientForDentistDto;
import com.dentalmanagementapp.dtos.record.PolyvalentRecordDto;
import com.dentalmanagementapp.entities.DentistPatient;
import com.dentalmanagementapp.entities.PolyvalentRecord;
import com.dentalmanagementapp.service.DentistPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolyvalentRecordMapper {
    private final DentistPatientService dentistPatientService;
    @Autowired
    public PolyvalentRecordMapper(DentistPatientService dentistPatientService) {
        this.dentistPatientService = dentistPatientService;
    }

    public PolyvalentRecordDto toDto(PolyvalentRecord entity) {
        PatientForDentistDto patientDto = new PatientForDentistDto(
                entity.getDentistPatient().getPatient().getFirstName(),
                entity.getDentistPatient().getPatient().getLastName(),
                entity.getDentistPatient().getLocalId()
        );
        return new PolyvalentRecordDto(
                patientDto,
                entity.getDescription(),
                entity.getRecordDate(),
                entity.getDiagnoseLegend()
        );
    }

    public PolyvalentRecord toEntity(PolyvalentRecordDto dto) {

        DentistPatient dentistPatient = dentistPatientService.getDentistPatientByPatientLocalId(dto.patientDto().localId());

        return new PolyvalentRecord(dto.legend(), dto.description(), dentistPatient);
    }

    public PolyvalentRecord toEntity(PolyvalentRecordUpdateDto recordUpdateDto, PolyvalentRecord record) {
        record.setDiagnoseLegend(recordUpdateDto.legend());
        record.setDescription(recordUpdateDto.description());
        return record;
    }
}
