package com.sirma.exam.validations;

import com.sirma.exam.model.Employee;

import java.time.LocalDate;

public class Validations {
    private void validateEmployee(Employee employee) {
        if (employee.getEmpId() == null) {
            throw new IllegalArgumentException("EmpId must not be null");
        }

        if (employee.getProjectId() == null) {
            throw new IllegalArgumentException("ProjectId must not be null");
        }

        validateDateRange(employee.getDateFrom(), "DateFrom");
        validateDateRange(employee.getDateTo(), "DateTo");
    }

    private void validateDateRange(LocalDate date, String fieldName) {
        if (date != null && date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(fieldName + " must be in the past or present");
        }
    }

}
