package com.dentalmanagementapp.repository.custom;

import com.dentalmanagementapp.entities.DentistPatient;
import com.dentalmanagementapp.util.FilterUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
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

        Map<String, String> params = new HashMap<>();
        String queryString = appendClauses(params, firstName, lastName);

        TypedQuery<DentistPatient> query = entityManager.createQuery(queryString, DentistPatient.class);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<DentistPatient> resultList = query.getResultList();

        String countQueryString = queryString.replace("SELECT dp", "SELECT COUNT(dp)");
        TypedQuery<Long> countQuery = entityManager.createQuery(countQueryString, Long.class);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        long total = countQuery.getSingleResult();

        return new PageImpl<>(resultList, pageable, total);
    }

    private String appendClauses(final Map<String, String> params, final String firstName, final String lastName) {
        StringBuilder queryBuilder = new StringBuilder("SELECT dp FROM DentistPatient dp WHERE");
        boolean moreThanOneClause = false;

        if (firstName != null && !firstName.isEmpty()) {
            queryBuilder.append(" dp.patient.firstName LIKE :firstName");
            moreThanOneClause = true;
            params.put("firstName", "%" + firstName + "%");
        }

        if (lastName != null && !lastName.isEmpty()) {
            if (moreThanOneClause) {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(" dp.patient.lastName LIKE :lastName");
            params.put("lastName", "%" + lastName + "%");
        }

        return queryBuilder.toString();
    }

    private void configureFilter() {
        filterUtil.configureFilter(entityManager);
    }
}
