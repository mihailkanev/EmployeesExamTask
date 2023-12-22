package com.sirma.exam.service;

import com.sirma.exam.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CsvLoaderService {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomCsvReader csvReader;
    @Value("${csv.file.path}")

    public void loadCsvData(String filePath) {
        try {
            CustomCsvReader csvReader = new CustomCsvReader();
            List<Employee> employees = csvReader.readCsv(filePath);

            employeeService.saveAll(employees);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception as needed
        }
    }
}
