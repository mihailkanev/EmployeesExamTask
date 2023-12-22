package com.sirma.exam.validations;

import com.sirma.exam.model.Employee;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Component
public class FilePathValidator {

    public void validateFilePath(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IOException("File path is empty or null");
        }
    }
    public void validateCsvData(List<Employee> employees) throws ValidationException {
        if (employees == null || employees.isEmpty()) {
            throw new ValidationException("CSV data is empty");
        }
    }
}
