package com.sirma.exam.validations;

import com.sirma.exam.model.Employee;
import jakarta.validation.ValidationException;

import java.io.IOException;
import java.util.List;

public class FilePathValidator {
    private void validateFilePath(String filePath) throws IOException {
        if (!filePath.isEmpty()) {
            throw new IOException("File path is empty");
        }
    }

    private void validateCsvData(List<Employee> employees) throws ValidationException {
        if (employees == null || employees.isEmpty()) {
            throw new ValidationException("CSV data is empty");
        }
    }
}