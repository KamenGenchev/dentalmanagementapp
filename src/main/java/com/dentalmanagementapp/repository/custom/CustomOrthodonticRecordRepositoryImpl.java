package com.dentalmanagementapp.repository.custom;

import com.dentalmanagementapp.entities.OrthodonticRecord;
import com.dentalmanagementapp.util.FilterUtil;
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

    private final FilterUtil filterUtil;

    @Autowired
    public CustomOrthodonticRecordRepositoryImpl(FilterUtil filterUtil) {
        this.filterUtil = filterUtil;
    }

    @Override
    public List<OrthodonticRecord> findAllWithFilter() {
        configureFilter();

        TypedQuery<OrthodonticRecord> query = entityManager.createQuery(
                "FROM OrthodonticRecord", OrthodonticRecord.class);
        return query.getResultList();
    }

    @Override
    public Optional<OrthodonticRecord> findOrthodonticRecordWithAccess(Long id) {
        configureFilter();

        TypedQuery<OrthodonticRecord> query = entityManager.createQuery(
                "SELECT o FROM OrthodonticRecord o WHERE o.id = :id",
                OrthodonticRecord.class);
        query.setParameter("id", id);

        return query.getResultList().stream().findFirst();
    }

    @Override
    public boolean existsByIdWithOwnership(Long id) {
        configureFilter();

        TypedQuery<Boolean> query = entityManager.createQuery(
                "SELECT EXISTS (SELECT 1 FROM OrthodonticRecord o WHERE o.id = :id)", Boolean.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    private void configureFilter() {
        filterUtil.configureFilter(entityManager);
    }


}
