package com.dentalmanagementapp.controllers;

import com.dentalmanagementapp.dtos.record.PolyvalentRecordDto;
import com.dentalmanagementapp.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/{id}")
    public ResponseEntity<PolyvalentRecordDto> getPolyvalentRecord(@PathVariable("id") Long id) {
        PolyvalentRecordDto record = recordService.getRecord(id);
        return ResponseEntity.ok(record);
    }

    @PostMapping()
    public ResponseEntity<PolyvalentRecordDto> createPolyvalentRecord(@Valid @RequestBody PolyvalentRecordDto record) {
        Long newRecordId = recordService.saveRecord(record);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newRecordId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<Void> updatePolyvalentRecord(@PathVariable("id") Long id, @Valid @RequestBody PolyvalentRecordDto record) {
        recordService.updateRecord(id, record);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolyvalentRecord(@PathVariable("id") Long id) {
        recordService.deleteRecord(id);

        return ResponseEntity.noContent().build();
    }


}


