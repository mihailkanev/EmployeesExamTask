package com.sirma.exam.service;

import com.sirma.exam.dto.EmployeeDTO;
import com.sirma.exam.model.Employee;
import com.sirma.exam.reposiory.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void saveAll(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    public List<EmployeeDTO> findLongestWorkingPair() {
        List<Employee> allEmployees = employeeRepository.findAll();
        List<EmployeeDTO> workingPairsDTO = new ArrayList<>();

        for (int i = 0; i < allEmployees.size(); i++) {
            for (int j = i + 1; j < allEmployees.size(); j++) {
                Employee employee1 = allEmployees.get(i);
                Employee employee2 = allEmployees.get(j);

                if (areEmployeesWorkingTogetherOnSameProject(employee1, employee2)) {
                    long overlapDays = calculateOverlapDays(
                            employee1.getDateFrom(), employee1.getDateTo(),
                            employee2.getDateFrom(), employee2.getDateTo()
                    );

                    // Enhanced logging
                    System.out.println("Pair: " + employee1.getEmpId() + " - " + employee2.getEmpId());
                    System.out.println("Employee1 Dates: " + employee1.getDateFrom() + " - " + employee1.getDateTo());
                    System.out.println("Employee2 Dates: " + employee2.getDateFrom() + " - " + employee2.getDateTo());

                    EmployeeDTO employeeDTO = createEmployeeDTO(employee1, employee2, overlapDays);
                    workingPairsDTO.add(employeeDTO);
                }
            }
        }
        System.out.println("Final Result: " + workingPairsDTO);

        return workingPairsDTO;
    }

    private boolean areEmployeesWorkingTogetherOnSameProject(Employee employee1, Employee employee2) {
        return !employee1.getEmpId().equals(employee2.getEmpId()) &&
                employee1.getProjectId().equals(employee2.getProjectId());
    }

    private EmployeeDTO createEmployeeDTO(Employee employee1, Employee employee2, long overlapDays) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpId1(employee1.getEmpId());
        employeeDTO.setEmpId2(employee2.getEmpId());
        employeeDTO.setProjectId(employee1.getProjectId());

        LocalDate overlapStart = calculateOverlapStart(employee1.getDateFrom(), employee2.getDateFrom());
        LocalDate overlapEnd = calculateOverlapEnd(employee1.getDateTo(), employee2.getDateTo());

        employeeDTO.setStartDate(overlapStart);
        employeeDTO.setEndDate(overlapEnd);
        employeeDTO.setOverlapDays(overlapDays);
        return employeeDTO;
    }

    private LocalDate calculateOverlapStart(LocalDate start1, LocalDate start2) {
        return start1.isAfter(start2) ? start1 : start2;
    }

    private LocalDate calculateOverlapEnd(LocalDate end1, LocalDate end2) {
        if (end1 == null || end2 == null) {
            return LocalDate.now(); // Връщайте текущата дата, когато една от двете дати е null
        }

        return end1.isBefore(end2) ? end1 : end2;
    }

    private long calculateOverlapDays(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        LocalDate overlapStart = calculateOverlapStart(start1, start2);
        LocalDate overlapEnd = calculateOverlapEnd(end1, end2);

        if (overlapStart.isAfter(overlapEnd)) {
            return 0;
        }
        return ChronoUnit.DAYS.between(overlapStart, overlapEnd);
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public void update(Long id, Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(id);
        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();

            // Use the Lombok-generated setEmpId method
            existingEmployee.setEmpId(updatedEmployee.getEmpId());

            // Set other fields similarly
            existingEmployee.setProjectId(updatedEmployee.getProjectId());
            existingEmployee.setDateFrom(updatedEmployee.getDateFrom());
            existingEmployee.setDateTo(updatedEmployee.getDateTo());

            employeeRepository.save(existingEmployee);
        }
    }
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }
}