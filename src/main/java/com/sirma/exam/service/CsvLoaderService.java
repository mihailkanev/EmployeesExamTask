package com.sirma.exam.service;

import com.sirma.exam.model.Employee;
import com.sirma.exam.validations.FilePathValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CsvLoaderService {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomCsvReader csvReader;

    @Autowired
    private FilePathValidator filePathValidator;
    @Value("${csv.file.path}")

    public void loadCsvData(String filePath) {
        try {

            filePathValidator.validateFilePath(filePath);
            Set<Long> existingEmployeeIds = new HashSet<>(employeeService.getAllEmployeeIds());
            CustomCsvReader csvReader = new CustomCsvReader();
            Map<Long, Employee> employees = csvReader.readCsv(filePath,existingEmployeeIds);

            employeeService.saveAll(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}