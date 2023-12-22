package com.sirma.exam.reposiory;

import com.sirma.exam.dto.EmployeeDTO;
import com.sirma.exam.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByEmpIdAndProjectIdOrderByDateFrom(Long empId, Long projectId);
}
