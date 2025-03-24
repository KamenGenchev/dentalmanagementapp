@FilterDefs({
        @FilterDef(
                name = "ownershipFilter",
                parameters = @ParamDef(name = "dentistId", type = Long.class),
                defaultCondition = "id IN (SELECT dp.id FROM dentist_patient dp WHERE dp.dentist_id = :dentistId)"
        ),
        @FilterDef(
                name = "patientFilter",
                parameters = @ParamDef(name = "patientId", type = Long.class),
                defaultCondition = "id IN (SELECT dp.id FROM dentist_patient dp WHERE dp.patient_id = :patientId)"
        )
})
package com.dentalmanagementapp.entities;

import org.hibernate.annotations.*;
