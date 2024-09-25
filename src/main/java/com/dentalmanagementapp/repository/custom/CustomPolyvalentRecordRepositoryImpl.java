package com.dentalmanagementapp.repository.custom;

import com.dentalmanagementapp.entities.PolyvalentRecord;
import com.dentalmanagementapp.util.FilterUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomPolyvalentRecordRepositoryImpl implements CustomPolyvalentRecordRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final FilterUtil filterUtil;

    @Autowired
    public CustomPolyvalentRecordRepositoryImpl(FilterUtil filterUtil) {
        this.filterUtil = filterUtil;
    }

    @Override
    public List<PolyvalentRecord> findAllWithFilter() {
        configureFilter();

        TypedQuery<PolyvalentRecord> query = entityManager.createQuery(
                "FROM PolyvalentRecord ", PolyvalentRecord.class);
        return query.getResultList();
    }

    @Override
    public Optional<PolyvalentRecord> findPolyvalentRecordWithAccess(Long id) {
        configureFilter();

        TypedQuery<PolyvalentRecord> query = entityManager.createQuery(
                "SELECT p FROM PolyvalentRecord p WHERE p.id = :id",
                PolyvalentRecord.class);
        query.setParameter("id", id);

        return query.getResultStream().findFirst();
    }

    @Override
    public boolean existsByIdWithOwnership(Long id) {
        configureFilter();

        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(p) FROM PolyvalentRecord p WHERE p.id = :id", Long.class);
        query.setParameter("id", id);

        return query.getSingleResult() > 0;
    }

    private void configureFilter(){
        filterUtil.configureFilter(entityManager);
    }
}
