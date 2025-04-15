package com.brandsmashers.employee_api.service;

import com.brandsmashers.employee_api.dto.EmployeeDTO;
import com.brandsmashers.employee_api.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface EmployeeService {

    EmployeeDTO saveEmployee(Employee employee);

    Optional<EmployeeDTO> getEmployeeById(Long id);

    EmployeeDTO updateEmployee(Long id, Employee updated);

    void deleteEmployee(Long id);

    Page<EmployeeDTO> getAllEmployees(String department, Double minSalary, Double maxSalary,
                                      LocalDate joinedAfter, LocalDate joinedBefore,
                                      Pageable pageable);
}
