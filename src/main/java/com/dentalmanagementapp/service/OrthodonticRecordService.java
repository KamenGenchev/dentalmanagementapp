package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.record.OrthodonticRecordCreateDto;
import com.dentalmanagementapp.dtos.record.OrthodonticRecordDto;
import com.dentalmanagementapp.dtos.record.OrthodonticRecordUpdateDto;
import jakarta.validation.Valid;

import java.util.List;

public interface OrthodonticRecordService {
    List<OrthodonticRecordDto> getAllRecords();
    List<OrthodonticRecordDto> getAllRecordsForPatient(short localPatientId);
    OrthodonticRecordDto getRecord(Long id);
    Long saveRecord(@Valid OrthodonticRecordCreateDto record);
    void updateRecord (Long id, @Valid OrthodonticRecordUpdateDto updateDto);
    void deleteRecord(Long id);

}
