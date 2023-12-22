package com.sirma.exam.controller;

import com.sirma.exam.dto.EmployeeDTO;
import com.sirma.exam.model.Employee;
import com.sirma.exam.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/longest-working-pair")
    public ResponseEntity<Object> getLongestWorkingPair() {
        try {
            List<EmployeeDTO> longestWorkingPair = employeeService.findLongestWorkingPair();
            return ResponseEntity.ok(longestWorkingPair);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during the calculation. Check the logs for more details.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
        try {
            Optional<Employee> employee = employeeService.findById(id);
            return employee.map(value -> ResponseEntity.ok((Object)value))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during the operation. Check the logs for more details.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
        try {
            employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body("Employee created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during the operation. Check the logs for more details.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        try {
            employeeService.update(id, updatedEmployee);
            return ResponseEntity.ok("Employee updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during the operation. Check the logs for more details.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteById(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during the operation. Check the logs for more details.");
        }
    }
}
