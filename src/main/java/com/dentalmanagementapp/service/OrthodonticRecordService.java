package com.dentalmanagementapp.service;

import com.dentalmanagementapp.dtos.record.OrthodonticRecordDto;
import com.dentalmanagementapp.dtos.record.OrthodonticRecordUpdateDto;
import com.dentalmanagementapp.security.CurrentUser;

import java.util.List;

public interface OrthodonticRecordService {
    List<OrthodonticRecordDto> getAllRecords(CurrentUser currentUser);
    OrthodonticRecordDto getRecord(Long id, CurrentUser currentUser);
    Long saveRecord(OrthodonticRecordDto record);
    void updateRecord (Long id, OrthodonticRecordUpdateDto updateDto, CurrentUser currentUser);
    void deleteRecord(Long id, CurrentUser currentUser);

}
