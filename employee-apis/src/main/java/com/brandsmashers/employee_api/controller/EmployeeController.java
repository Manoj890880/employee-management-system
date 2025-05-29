package com.brandsmashers.employee_api.controller;

import com.brandsmashers.employee_api.dto.EmployeeDTO;
import com.brandsmashers.employee_api.model.Employee;
import com.brandsmashers.employee_api.service.EmployeeService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

  private final EmployeeService employeeService;
  private final ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
    Employee employee = modelMapper.map(employeeDTO, Employee.class);
    EmployeeDTO savedEmployee = employeeService.saveEmployee(employee);
    return ResponseEntity.ok(savedEmployee);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
    Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
    return employeeDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeDTO> updateEmployee(
      @PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
    Employee employee = modelMapper.map(employeeDTO, Employee.class);
    EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employee);
    return ResponseEntity.ok(updatedEmployee);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
    employeeService.deleteEmployee(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
      @RequestParam(required = false) String department,
      @RequestParam(required = false) Double minSalary,
      @RequestParam(required = false) Double maxSalary,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate joinedAfter,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate joinedBefore,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort) {
    Sort.Direction direction =
        sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));
    Page<EmployeeDTO> result =
        employeeService.getAllEmployees(
            department, minSalary, maxSalary, joinedAfter, joinedBefore, pageable);
    return ResponseEntity.ok(result);
  }

  @PostMapping("/increment-salary")
  public ResponseEntity<String> incrementSalary(
      @RequestParam String department, @RequestParam Double percent) {
    employeeService.increaseSalary(department, percent);
    return ResponseEntity.ok("Salary increment done.");
  }

  @GetMapping("/total-salary")
  public ResponseEntity<String> totalSalary(@RequestParam String department) {
    Double total = employeeService.getTotalSalaryByDepartment(department);
    return ResponseEntity.ok("Total salary for " + department + ": " + total);
  }
}
