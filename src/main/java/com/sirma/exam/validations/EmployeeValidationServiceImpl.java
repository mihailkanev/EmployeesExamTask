package com.sirma.exam.validations;

import com.sirma.exam.model.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmployeeValidationServiceImpl implements EmployeeValidationService {

    @Override
    public void validateEmployee(Employee employee) {
        validateId(employee.getEmpId(), "EmpId");
        validateId(employee.getProjectId(), "ProjectId");

        validateDateRange(employee.getDateFrom(), "DateFrom");
        validateDateRange(employee.getDateTo(), "DateTo");
    }

    private void validateId(Long id, String fieldName) {
        if (id == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
    }

    private void validateDateRange(LocalDate date, String fieldName) {
        if (date != null && date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(fieldName + " must be in the past or present");
        }
    }
}
