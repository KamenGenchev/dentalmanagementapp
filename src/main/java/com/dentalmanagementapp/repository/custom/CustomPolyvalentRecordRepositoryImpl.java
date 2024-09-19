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
                "SELECT o FROM PolyvalentRecord o WHERE o.id = :id",
                PolyvalentRecord.class);
        query.setParameter("id", id);

        return query.getResultList().stream().findFirst();
    }

    @Override
    public boolean existsByIdWithOwnership(Long id) {
        configureFilter();

        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT EXISTS(SELECT 1 FROM PolyvalentRecord o WHERE o.id = :id)", Long.class);
        query.setParameter("id", id);
        Long count = query.getSingleResult();
        return count > 0;
    }

    private void configureFilter(){
        filterUtil.configureFilter(entityManager);
    }
}
