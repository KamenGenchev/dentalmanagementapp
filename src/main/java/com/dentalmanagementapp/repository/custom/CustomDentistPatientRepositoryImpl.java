package com.dentalmanagementapp.repository.custom;

import com.dentalmanagementapp.entities.DentistPatient;
import com.dentalmanagementapp.util.FilterUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CustomDentistPatientRepositoryImpl implements CustomDentistPatientRepository {
    private final FilterUtil filterUtil;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CustomDentistPatientRepositoryImpl(FilterUtil filterUtil) {
        this.filterUtil = filterUtil;
    }

    @Override
    public short findNextLocalIdForDentist() {
        configureFilter();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Short> query = cb.createQuery(Short.class);
        Root<DentistPatient> root = query.from(DentistPatient.class);
        query.select(cb.coalesce(cb.greatest(root.get("localId")), cb.literal((short) 0)));

        Short maxLocalId = entityManager.createQuery(query).getSingleResult();
        return (short) (maxLocalId + 1);
    }

    @Override
    public boolean patientExistsForDentist(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        configureFilter();

        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(dp) FROM DentistPatient dp WHERE dp.patient.email = :email",
                Long.class);
        query.setParameter("email", email);

        return query.getSingleResult() > 0;
    }

    @Override
    public Page<DentistPatient> searchPatientsByName(String firstName, String lastName, Pageable pageable) {
        configureFilter();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DentistPatient> query = cb.createQuery(DentistPatient.class);
        Root<DentistPatient> root = query.from(DentistPatient.class);

        List<Predicate> predicates = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(cb.like(root.get("patient").get("firstName"), "%" + firstName + "%"));
        }
        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(cb.like(root.get("patient").get("lastName"), "%" + lastName + "%"));
        }
        query.select(root).where(predicates.toArray(new Predicate[0]));

        TypedQuery<DentistPatient> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<DentistPatient> resultList = typedQuery.getResultList();


        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(DentistPatient.class)))
                .where(predicates.toArray(new Predicate[0]));
        long total = entityManager.createQuery(countQuery).getSingleResult();


        return new PageImpl<>(resultList, pageable, total);
    }


    private void configureFilter() {
        filterUtil.configureFilter(entityManager);
    }
}
