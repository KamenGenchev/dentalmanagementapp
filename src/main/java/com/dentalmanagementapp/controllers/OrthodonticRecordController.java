package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.OrthodonticRecordDto;
import com.dentalmanagementapp.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/records/orthodontic")
@RestController
public class OrthodonticRecordController {
    private final RecordService<OrthodonticRecordDto> recordService;

    @Autowired
    public OrthodonticRecordController(RecordService<OrthodonticRecordDto> recordService) {
        this.recordService = recordService;
    }

    @GetMapping()
    public ResponseEntity<List<OrthodonticRecordDto>> listOrthodonticRecords() {
        List<OrthodonticRecordDto> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }
}
