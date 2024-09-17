package com.dentalmanagementapp.service.implementation;

import com.dentalmanagementapp.dtos.record.OrthodonticRecordDto;
import com.dentalmanagementapp.dtos.record.OrthodonticRecordUpdateDto;
import com.dentalmanagementapp.entities.OrthodonticRecord;
import com.dentalmanagementapp.exception.custom.NotFoundException;
import com.dentalmanagementapp.mappers.OrthodonticRecordMapper;
import com.dentalmanagementapp.repository.OrthodonticRecordRepository;
import com.dentalmanagementapp.security.CurrentUser;
import com.dentalmanagementapp.service.OrthodonticRecordService;
import com.dentalmanagementapp.validation.OrthodonticRecordValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class OrthodonticRecordServiceImpl implements OrthodonticRecordService {
    private final OrthodonticRecordRepository orthodonticRecordRepository;
    private final OrthodonticRecordMapper orthodonticRecordMapper;
    private final OrthodonticRecordValidation orthodonticRecordValidation;

    @Autowired
    public OrthodonticRecordServiceImpl(OrthodonticRecordRepository orthodonticRecordRepository, OrthodonticRecordMapper orthodonticRecordMapper, OrthodonticRecordValidation orthodonticRecordValidation) {
        this.orthodonticRecordRepository = orthodonticRecordRepository;
        this.orthodonticRecordMapper = orthodonticRecordMapper;
        this.orthodonticRecordValidation = orthodonticRecordValidation;
    }

    @Override
    @Transactional
    public List<OrthodonticRecordDto> getAllRecords(CurrentUser currentUser) {
        orthodonticRecordValidation.validateCurrentUser(currentUser);

        return orthodonticRecordRepository.findAllWithFilter(
                        currentUser.currentUserId(),
                        currentUser.isAdmin()
                ).stream()
                .map(orthodonticRecordMapper::toDto)
                .collect(Collectors.collectingAndThen(Collectors.toList(), List::copyOf));
    }

    @Override
    @Transactional(readOnly = true)
    public OrthodonticRecordDto getRecord(Long id, CurrentUser currentUser) {
        orthodonticRecordValidation.requireNonNull(id, "Record ID cannot be null");
        orthodonticRecordValidation.validateCurrentUser(currentUser);

        return orthodonticRecordRepository.findOrthodonticRecordWithAccess(
                        id,
                        currentUser.currentUserId(),
                        currentUser.isAdmin()
                )
                .map(orthodonticRecordMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Orthodontic record with id: " + id + " was not found"));
    }

    @Override
    public Long saveRecord(@Valid OrthodonticRecordDto recordDto) {
        OrthodonticRecord record = orthodonticRecordMapper.toEntity(recordDto);
        return orthodonticRecordRepository.save(record).getId();
    }

    @Override
    public void updateRecord(Long id, @Valid OrthodonticRecordUpdateDto recordUpdateDto, CurrentUser currentUser) {
        orthodonticRecordValidation.requireNonNull(id, "Record ID cannot be null");
        orthodonticRecordValidation.validateCurrentUser(currentUser);

        OrthodonticRecord record = orthodonticRecordRepository.findOrthodonticRecordWithAccess(
                        id,
                        currentUser.currentUserId(),
                        currentUser.isAdmin()
                )
                .orElseThrow(() -> new NotFoundException("Orthodontic record with id: " + id + " was not found"));

        record = orthodonticRecordMapper.toEntity(recordUpdateDto, record);

        orthodonticRecordRepository.save(record);
    }

    @Override
    public void deleteRecord(Long id, CurrentUser currentUser) {
        orthodonticRecordValidation.requireNonNull(id, "Record ID cannot be null");
        orthodonticRecordValidation.validateCurrentUser(currentUser);
        orthodonticRecordValidation.validateRecordAccess(id, currentUser);

        orthodonticRecordRepository.deleteById(id);
    }
}