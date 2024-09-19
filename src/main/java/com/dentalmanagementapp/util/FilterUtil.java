package com.dentalmanagementapp.util;

import com.dentalmanagementapp.security.CurrentUser;
import com.dentalmanagementapp.security.UserContext;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class FilterUtil {

    public void configureFilter(EntityManager entityManager) {
        Session session = entityManager.unwrap(Session.class);

        CurrentUser currentUser = UserContext.getCurrentUser();
        Long userId = currentUser.currentUserId();

        if (currentUser.isAdmin()) {
            session.disableFilter("ownershipFilter");
            session.disableFilter("patientFilter");
        } else {
            if (currentUser.isPatient()) {
                session.enableFilter("patientFilter").setParameter("patientId", userId);
                session.disableFilter("ownershipFilter");
            }
            session.enableFilter("ownershipFilter").setParameter("dentistId", userId);
            session.disableFilter("patientFilter");
        }
    }

}