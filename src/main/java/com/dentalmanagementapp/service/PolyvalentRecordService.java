package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.record.PolyvalentRecordUpdateDto;

import java.util.List;

public interface PolyvalentRecordService {
    List<PolyvalentRecordUpdateDto> getAllRecords();
    PolyvalentRecordUpdateDto getRecord(Long id);
    Long saveRecord(PolyvalentRecordUpdateDto record);
    void updateRecord (Long id, PolyvalentRecordUpdateDto updateDto);
    void deleteRecord(Long id);

}
