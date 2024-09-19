package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.record.OrthodonticRecordDto;
import com.dentalmanagementapp.dtos.record.OrthodonticRecordUpdateDto;
import com.dentalmanagementapp.service.implementation.OrthodonticRecordServiceImpl;
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
    private final OrthodonticRecordServiceImpl recordService;

    @Autowired
    public OrthodonticRecordController(OrthodonticRecordServiceImpl recordService) {
        this.recordService = recordService;
    }

    @GetMapping()
    public ResponseEntity<List<OrthodonticRecordDto>> listOrthodonticRecords() {
        List<OrthodonticRecordDto> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrthodonticRecordDto> getOrthodonticRecord(@PathVariable("id") Long id) {
        OrthodonticRecordDto record = recordService.getRecord(id);
        return ResponseEntity.ok(record);
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
    public ResponseEntity<Void> updateOrthodonticRecord(@PathVariable("id") Long id, @Valid @RequestBody OrthodonticRecordUpdateDto updateDto) {
        recordService.updateRecord(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrthodonticRecord(@PathVariable("id") Long id) {
        recordService.deleteRecord(id);

        return ResponseEntity.noContent().build();
    }

}
