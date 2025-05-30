package com.brandsmashers.employee_api.service;

import com.brandsmashers.employee_api.dto.EmployeeDTO;
import com.brandsmashers.employee_api.model.Employee;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

  EmployeeDTO saveEmployee(Employee employee);

  Optional<EmployeeDTO> getEmployeeById(Long id);

  EmployeeDTO updateEmployee(Long id, Employee updated);

  void deleteEmployee(Long id);

  Page<EmployeeDTO> getAllEmployees(
      String department,
      Double minSalary,
      Double maxSalary,
      LocalDate joinedAfter,
      LocalDate joinedBefore,
      Pageable pageable);

  void increaseSalary(String department, Double percent);

  Double getTotalSalaryByDepartment(String department);

  byte[] generateEmployeePdf(EmployeeDTO employeeDTO) throws IOException;
}
