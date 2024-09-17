package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.PolyvalentUpdateDto;
import com.dentalmanagementapp.dtos.record.PolyvalentRecordDto;
import com.dentalmanagementapp.security.CurrentUser;

import java.util.List;

public interface PolyvalentRecordService {
    List<PolyvalentRecordDto> getAllRecords(CurrentUser currentUser);
    PolyvalentRecordDto getRecord(Long id, CurrentUser currentUser);
    Long saveRecord(PolyvalentRecordDto record);
    void updateRecord (Long id, PolyvalentUpdateDto updateDto, CurrentUser currentUser);
    void deleteRecord(Long id, CurrentUser currentUser);

}
