package com.dentalmanagementapp.dtos;

import com.dentalmanagementapp.dtos.record.OrthodonticRecordDto;
import com.dentalmanagementapp.dtos.record.PolyvalentRecordDto;

import java.util.List;

public record PatientRecordsDto(
        List<PolyvalentRecordDto> polyvalentRecords,
        List<OrthodonticRecordDto> orthodonticRecords) {
}
