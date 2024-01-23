package com.sirma.exam.service;

import com.sirma.exam.dto.EmployeeDTO;
import com.sirma.exam.model.Employee;
import com.sirma.exam.reposiory.EmployeeRepository;
import com.sirma.exam.validations.EmployeeValidationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeValidationService employeeValidationService;
    @Autowired
    private EmployeeRepository employeeRepository;

    private Set<Long> processedEmployeeIds = new HashSet<>();


    @Transactional
    public void saveAll(Map<Long, Employee> employees) {
        for (Employee employee : employees.values()) {
            Long empId = employee.getEmpId();

            if (!processedEmployeeIds.contains(empId)) {
                Optional<Employee> existingEmployee = employeeRepository.findById(empId);

                if (existingEmployee.isEmpty()) {
                    employeeRepository.save(employee);
                } else {
                    existingEmployee.ifPresent(emp -> updateEmployee(emp, employee));
                }

                processedEmployeeIds.add(empId);
            }
        }
    }
    private void updateEmployee(Employee existEmployee, Employee newEmployee) {
        existEmployee.setProjectId(newEmployee.getProjectId());
        existEmployee.setDateFrom(newEmployee.getDateFrom());
        existEmployee.setDateTo(newEmployee.getDateTo());
        employeeRepository.save(existEmployee);
    }

    public EmployeeDTO findLongestWorkingPair() {
        List<Employee> allEmployees = employeeRepository.findAll();
        EmployeeDTO maxOverlapPair = null;
        long maxOverlapDays = 0;

        for (int i = 0; i < allEmployees.size(); i++) {
            for (int j = i + 1; j < allEmployees.size(); j++) {
                Employee employee1 = allEmployees.get(i);
                Employee employee2 = allEmployees.get(j);

                if (areEmployeesWorkingTogetherOnSameProject(employee1, employee2)) {
                    long overlapDays = calculateOverlapDays(
                            employee1.getDateFrom(), employee1.getDateTo(),
                            employee2.getDateFrom(), employee2.getDateTo()
                    );

                    if (overlapDays > maxOverlapDays) {
                        maxOverlapDays = overlapDays;
                        maxOverlapPair = createEmployeeDTO(employee1, employee2, overlapDays);
                    }
                }
            }
        }

        return maxOverlapPair;
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
            return LocalDate.now();
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

            existingEmployee.setEmpId(updatedEmployee.getEmpId());

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
        employeeValidationService.validateEmployee(employee);
    }

    public List<Long> getAllEmployeeIds() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(Employee::getEmpId)
                .collect(Collectors.toList());
    }
}