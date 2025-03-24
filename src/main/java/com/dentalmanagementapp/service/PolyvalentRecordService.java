package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.PolyvalentRecordUpdateDto;
import com.dentalmanagementapp.dtos.record.PolyvalentRecordDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PolyvalentRecordService {
    List<PolyvalentRecordDto> getAllRecords();
    PolyvalentRecordDto getRecord(Long id);
    Long saveRecord(@Valid PolyvalentRecordDto record);
    void updateRecord(Long id, @Valid PolyvalentRecordUpdateDto recordUpdateDto);
    void deleteRecord(Long id);

}
