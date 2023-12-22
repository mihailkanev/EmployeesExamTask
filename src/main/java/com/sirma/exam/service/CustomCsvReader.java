package com.sirma.exam.service;

import com.sirma.exam.model.Employee;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class CustomCsvReader {
    public List<Employee> readCsv(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                processCsvLine(line, employees);
            }
        }

        return employees;
    }

    private void processCsvLine(String line, List<Employee> employees) {
        String[] data = line.split(",");

        long employeeId = Long.parseLong(data[0].trim());
        long projectId = Long.parseLong(data[1].trim());
        LocalDate startDate = parseDate(data[2].trim());
        LocalDate endDate = parseDate(data[3]);
            Employee employee = new Employee();
            employee.setEmpId(employeeId);
            employee.setProjectId(projectId);
            employee.setDateFrom(startDate);
            employee.setDateTo(endDate);

            employees.add(employee);

    }
    private static LocalDate parseDate(String dateString) {
        if (dateString != null && !dateString.trim().equalsIgnoreCase("NULL")) {

            String[] dateFormats = {
                    "yyyy-MM-dd",
                    "MM/dd/yyyy",
                    "yyyyMMdd",
                    "dd/MM/yyyy",
                    "yyyy/MM/dd",
                    "dd-MMM-yyyy",
                    "dd.MM.yyyy",  // Bulgarian date format example
                    "yyyy.MM.dd"   // Another Bulgarian date format example
            };

            for (String format : dateFormats) {
                try {
                    DateTimeFormatter dateFormat;
                    if (format.equals("dd-MMM-yyyy")) {
                        dateFormat = DateTimeFormatter.ofPattern(format, new Locale("en", "US"));
                    } else {
                        dateFormat = DateTimeFormatter.ofPattern(format);
                    }
                    return LocalDate.parse(dateString.trim(), dateFormat);
                } catch (DateTimeParseException e) {

                }
            }
        }
        return LocalDate.now();
    }
}
