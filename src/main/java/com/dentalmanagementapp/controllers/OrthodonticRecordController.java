package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.OrthodonticRecordDto;
import com.dentalmanagementapp.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/{id}")
    public ResponseEntity<List<OrthodonticRecordDto>> getOrthodonticRecord(@PathVariable("id") Long id) {
        List<OrthodonticRecordDto> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    @PostMapping()
    public ResponseEntity<OrthodonticRecordDto> createOrthodonticRecord(@Valid @RequestBody OrthodonticRecordDto record) {
        Long newRecordId = recordService.saveRecord(record);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newRecordId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Void> updateOrthodonticRecord(@PathVariable("id") Long id, @Valid @RequestBody OrthodonticRecordDto record) {
        recordService.updateRecord(record);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrthodonticRecord(@PathVariable("id") Long id, @Valid @RequestBody OrthodonticRecordDto record) {
        recordService.deleteRecord(id);

        return ResponseEntity.noContent().build();
    }

}
