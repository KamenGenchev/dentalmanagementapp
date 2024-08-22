package com.dentalmanagementapp.service;

import com.dentalmanagementapp.entities.OrthodonticRecord;
import com.dentalmanagementapp.repository.OrthodonticRecordRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class OrthodonticRecordServiceImpl {
    private final OrthodonticRecordRepository orthodonticRecordRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public OrthodonticRecordServiceImpl(OrthodonticRecordRepository orthodonticRecordRepository) {
        this.orthodonticRecordRepository = orthodonticRecordRepository;
    }

    @Transactional
    public List<OrthodonticRecord> getAllSortedByDoctorAndPatient(short patientId, Long doctorId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrthodonticRecord> query = cb.createQuery(OrthodonticRecord.class);
        Root<OrthodonticRecord> root = query.from(OrthodonticRecord.class);

        Predicate predicateForDoctorId = cb.equal(root.get("doctorId"), doctorId);

        query.where(predicateForDoctorId);
        query.orderBy(cb.asc(root.get("patientId")));

        return entityManager.createQuery(query).getResultList();
    }
}