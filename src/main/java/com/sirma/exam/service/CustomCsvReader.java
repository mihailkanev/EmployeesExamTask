package com.sirma.exam.service;

import com.sirma.exam.model.Employee;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Component
public class CustomCsvReader {
        private Set<Long> processedEmployeeIds = new HashSet<>();
        public Map<Long, Employee> readCsv(String filePath) throws IOException {
            Map<Long, Employee> employeeMap = new HashMap<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;

                while ((line = br.readLine()) != null) {
                    try {
                        processCsvLine(line, employeeMap);
                    } catch (Exception e) {
                        System.err.println("Error processing CSV line: " + line);
                        e.printStackTrace();
                    }
                }
            }

            return employeeMap;
        }

    private void processCsvLine(String line, Map<Long, Employee> employeeMap) {
        String[] data = line.split(",");

        if (data.length != 4) {
            System.err.println("Invalid CSV line: " + line);
            return;
        }

        try {
            long employeeId = Long.parseLong(data[0].trim());
            long projectId = Long.parseLong(data[1].trim());
            LocalDate startDate = parseDate(data[2].trim());
            LocalDate endDate = parseDate(data[3].trim());

            if (employeeMap.containsKey(employeeId)) {
                Employee existingEmployee = employeeMap.get(employeeId);

                if (!existingEmployee.getProjectId().equals(projectId)
                        || !existingEmployee.getDateFrom().equals(startDate)
                        || !existingEmployee.getDateTo().equals(endDate)) {

                    existingEmployee.setProjectId(projectId);
                    existingEmployee.setDateFrom(startDate);
                    existingEmployee.setDateTo(endDate);
                }

                return;
            }

            Employee employee = new Employee();
            employee.setEmpId(employeeId);
            employee.setProjectId(projectId);
            employee.setDateFrom(startDate);
            employee.setDateTo(endDate);

            processedEmployeeIds.add(employeeId);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing CSV line: " + line);
        }
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
                    "dd.MM.yyyy",
                    "yyyy.MM.dd"
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
