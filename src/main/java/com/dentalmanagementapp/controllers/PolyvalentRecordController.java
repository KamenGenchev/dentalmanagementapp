package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.PolyvalentRecordDto;
import com.dentalmanagementapp.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/records/polyvalent")
public class PolyvalentRecordController {
    private final RecordService<PolyvalentRecordDto> recordService;

    public PolyvalentRecordController(RecordService<PolyvalentRecordDto> recordService) {
        this.recordService = recordService;
    }

    @GetMapping()
    public ResponseEntity<List<PolyvalentRecordDto>> listPolyvalentRecords() {
        List<PolyvalentRecordDto> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }


}


