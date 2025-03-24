package com.dentalmanagementapp.repository.custom;

import com.dentalmanagementapp.entities.OrthodonticRecord;
import com.dentalmanagementapp.config.FilterConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomOrthodonticRecordRepositoryImpl implements CustomOrthodonticRecordRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final FilterConfig filterConfig;

    @Autowired
    public CustomOrthodonticRecordRepositoryImpl(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public List<OrthodonticRecord> findAllWithFilter() {
        configureFilter();

        TypedQuery<OrthodonticRecord> query = entityManager.createQuery(
                "FROM OrthodonticRecord", OrthodonticRecord.class);
        return query.getResultList();
    }

    @Override
    public List<OrthodonticRecord> findAllWithFilter(short localPatientId) {
        configureFilter();

        TypedQuery<OrthodonticRecord> query = entityManager.createQuery(
                "SELECT o FROM OrthodonticRecord o WHERE o.dentistPatient.localId = :localPatientId",
                OrthodonticRecord.class);
        query.setParameter("localPatientId", localPatientId);

        return query.getResultList();
    }

    @Override
    public Optional<OrthodonticRecord> findOrthodonticRecordWithAccess(Long id) {
        configureFilter();

        TypedQuery<OrthodonticRecord> query = entityManager.createQuery(
                "SELECT o FROM OrthodonticRecord o WHERE o.id = :id",
                OrthodonticRecord.class);
        query.setParameter("id", id);

        return query.getResultStream().findFirst();
    }

    @Override
    public boolean existsByIdWithOwnership(Long id) {
        configureFilter();

        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(o) FROM OrthodonticRecord o WHERE o.id = :id", Long.class);
        query.setParameter("id", id);

        return query.getSingleResult() > 0;
    }

    private void configureFilter() {
        filterConfig.configureFilter(entityManager);
    }


}
