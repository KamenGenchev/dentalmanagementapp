package com.dentalmanagementapp.service.implementation;

import com.dentalmanagementapp.dtos.PolyvalentRecordUpdateDto;
import com.dentalmanagementapp.dtos.record.PolyvalentRecordDto;
import com.dentalmanagementapp.entities.PolyvalentRecord;
import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.mappers.PolyvalentRecordMapper;
import com.dentalmanagementapp.repository.PolyvalentRecordRepository;
import com.dentalmanagementapp.service.PolyvalentRecordService;
import com.dentalmanagementapp.validation.PolyvalentRecordValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class PolyvalentRecordServiceImpl implements PolyvalentRecordService {
    private final PolyvalentRecordRepository polyvalentRecordRepository;
    private final PolyvalentRecordMapper polyvalentRecordMapper;
    private final PolyvalentRecordValidation polyvalentRecordValidation;

    @Autowired
    public PolyvalentRecordServiceImpl(PolyvalentRecordRepository polyvalentRecordRepository, PolyvalentRecordMapper polyvalentRecordMapper, PolyvalentRecordValidation polyvalentRecordValidation) {
        this.polyvalentRecordRepository = polyvalentRecordRepository;
        this.polyvalentRecordMapper = polyvalentRecordMapper;
        this.polyvalentRecordValidation = polyvalentRecordValidation;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PolyvalentRecordDto> getAllRecords() {
        return polyvalentRecordRepository.findAllWithFilter().stream()
                .map(polyvalentRecordMapper::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), List::copyOf));
    }

    @Override
    @Transactional(readOnly = true)
    public PolyvalentRecordDto getRecord(Long id) {
        polyvalentRecordValidation.requireNonNullId(id);
        return polyvalentRecordRepository.findPolyvalentRecordWithAccess(id)
                .map(polyvalentRecordMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Record with id: " + id + " was not found"));
    }

    @Override
    public Long saveRecord(@Valid PolyvalentRecordDto recordDto) {
        PolyvalentRecord record = polyvalentRecordMapper.toEntity(recordDto);
        return polyvalentRecordRepository.save(record).getId();
    }


    @Override
    @Transactional
    public void updateRecord(Long id, @Valid PolyvalentRecordUpdateDto recordUpdateDto) {
        polyvalentRecordValidation.requireNonNullId(id);

        PolyvalentRecord record = polyvalentRecordRepository.findPolyvalentRecordWithAccess(id)
                .orElseThrow(() -> new NotFoundException("Polyvalent record with id: " + id + " was not found"));

        record = polyvalentRecordMapper.toEntity(recordUpdateDto, record);
        polyvalentRecordRepository.save(record);
    }

    @Override
    @Transactional
    public void deleteRecord(Long id) {
        polyvalentRecordValidation.requireNonNullId(id);
        polyvalentRecordValidation.validateRecordAccess(id);

        polyvalentRecordRepository.deleteById(id);
    }


}
