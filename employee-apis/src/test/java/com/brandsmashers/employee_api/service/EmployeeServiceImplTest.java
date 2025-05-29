package com.brandsmashers.employee_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.brandsmashers.employee_api.dto.EmployeeDTO;
import com.brandsmashers.employee_api.exception.ResourceNotFoundException;
import com.brandsmashers.employee_api.model.Employee;
import com.brandsmashers.employee_api.repository.EmployeeRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

  @Mock private EmployeeRepository employeeRepository;

  @Mock private ModelMapper modelMapper;

  @InjectMocks private EmployeeServiceImpl employeeService;

  @Captor private ArgumentCaptor<Specification<Employee>> specCaptor;

  private Employee employee;
  private EmployeeDTO employeeDTO;

  @BeforeEach
  public void setup() {
    // Create sample employee data for tests
    employee = new Employee();
    employee.setId(1L);
    employee.setName("John Doe");
    employee.setEmail("john.doe@example.com");
    employee.setDepartment("IT");
    employee.setSalary(75000.0);
    employee.setJoiningDate(LocalDate.of(2023, 1, 15));

    employeeDTO = new EmployeeDTO();
    employeeDTO.setId(1L);
    employeeDTO.setName("John Doe");
    employeeDTO.setEmail("john.doe@example.com");
    employeeDTO.setDepartment("IT");
    employeeDTO.setSalary(75000.0);
    employeeDTO.setJoiningDate(LocalDate.of(2023, 1, 15));
  }

  @Test
  @DisplayName("Test save employee - success")
  public void testSaveEmployee() {
    // Arrange
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
    when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

    // Act
    EmployeeDTO result = employeeService.saveEmployee(employee);

    // Assert
    assertNotNull(result);
    assertEquals(employeeDTO, result);
    verify(employeeRepository, times(1)).save(employee);
    verify(modelMapper, times(1)).map(employee, EmployeeDTO.class);
  }

  @Test
  @DisplayName("Test get employee by ID - found")
  public void testGetEmployeeById_Found() {
    // Arrange
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
    when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

    // Act
    Optional<EmployeeDTO> result = employeeService.getEmployeeById(1L);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(employeeDTO, result.get());
    verify(employeeRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Test get employee by ID - not found")
  public void testGetEmployeeById_NotFound() {
    // Arrange
    when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

    // Act
    Optional<EmployeeDTO> result = employeeService.getEmployeeById(99L);

    // Assert
    assertFalse(result.isPresent());
    verify(employeeRepository, times(1)).findById(99L);
    verify(modelMapper, never()).map(any(), any());
  }

  @Test
  @DisplayName("Test update employee - success")
  public void testUpdateEmployee_Success() {
    // Arrange
    Employee updatedEmployee = new Employee();
    updatedEmployee.setId(1L);
    updatedEmployee.setName("John Updated");
    updatedEmployee.setEmail("john.updated@example.com");
    updatedEmployee.setDepartment("Management");
    updatedEmployee.setSalary(85000.0);
    updatedEmployee.setJoiningDate(LocalDate.of(2023, 1, 15));

    EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
    updatedEmployeeDTO.setId(1L);
    updatedEmployeeDTO.setName("John Updated");
    updatedEmployeeDTO.setEmail("john.updated@example.com");
    updatedEmployeeDTO.setDepartment("Management");
    updatedEmployeeDTO.setSalary(85000.0);
    updatedEmployeeDTO.setJoiningDate(LocalDate.of(2023, 1, 15));

    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
    when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);
    when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class)))
        .thenReturn(updatedEmployeeDTO);

    // Act
    EmployeeDTO result = employeeService.updateEmployee(1L, updatedEmployee);

    // Assert
    assertNotNull(result);
    assertEquals(updatedEmployeeDTO, result);

    // Verify that employee was updated with new values
    ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
    verify(employeeRepository).save(employeeCaptor.capture());

    Employee capturedEmployee = employeeCaptor.getValue();
    assertEquals("John Updated", capturedEmployee.getName());
    assertEquals("john.updated@example.com", capturedEmployee.getEmail());
    assertEquals("Management", capturedEmployee.getDepartment());
    assertEquals(85000.0, capturedEmployee.getSalary());
  }

  @Test
  @DisplayName("Test update employee - not found")
  public void testUpdateEmployee_NotFound() {
    // Arrange
    when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          employeeService.updateEmployee(99L, employee);
        });

    verify(employeeRepository, never()).save(any());
  }

  @Test
  @DisplayName("Test delete employee - success")
  public void testDeleteEmployee_Success() {
    // Arrange
    when(employeeRepository.existsById(1L)).thenReturn(true);
    doNothing().when(employeeRepository).deleteById(1L);

    // Act
    employeeService.deleteEmployee(1L);

    // Assert
    verify(employeeRepository, times(1)).existsById(1L);
    verify(employeeRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Test delete employee - not found")
  public void testDeleteEmployee_NotFound() {
    // Arrange
    when(employeeRepository.existsById(99L)).thenReturn(false);

    // Act & Assert
    assertThrows(
        ResourceNotFoundException.class,
        () -> {
          employeeService.deleteEmployee(99L);
        });

    verify(employeeRepository, never()).deleteById(anyLong());
  }

  @Test
  @DisplayName("Test get all employees with filters")
  public void testGetAllEmployeesWithFilters() {
    // Arrange
    String department = "IT";
    Double minSalary = 50000.0;
    Double maxSalary = 100000.0;
    LocalDate joinedAfter = LocalDate.of(2023, 1, 1);
    LocalDate joinedBefore = LocalDate.of(2023, 12, 31);
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));

    List<Employee> employees = Arrays.asList(employee);
    Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

    when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(employeePage);
    when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

    // Act
    Page<EmployeeDTO> result =
        employeeService.getAllEmployees(
            department, minSalary, maxSalary, joinedAfter, joinedBefore, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(employeeDTO, result.getContent().get(0));

    // Verify specification was built correctly
    verify(employeeRepository).findAll(specCaptor.capture(), eq(pageable));

    // We can't directly test the content of the specification, but we can verify it was created
    assertNotNull(specCaptor.getValue());
  }

  @Test
  @DisplayName("Test get all employees without filters")
  public void testGetAllEmployeesWithoutFilters() {
    // Arrange
    Pageable pageable = PageRequest.of(0, 10);

    List<Employee> employees = Arrays.asList(employee);
    Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

    when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(employeePage);
    when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(employeeDTO);

    // Act
    Page<EmployeeDTO> result =
        employeeService.getAllEmployees(null, null, null, null, null, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(employeeDTO, result.getContent().get(0));

    // Verify specification was built correctly
    verify(employeeRepository).findAll(specCaptor.capture(), eq(pageable));
  }

  @Test
  @DisplayName("Test get all employees - empty result")
  public void testGetAllEmployees_EmptyResult() {
    // Arrange
    Pageable pageable = PageRequest.of(0, 10);

    List<Employee> employees = List.of();
    Page<Employee> employeePage = new PageImpl<>(employees, pageable, 0);

    when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(employeePage);

    // Act
    Page<EmployeeDTO> result =
        employeeService.getAllEmployees(null, null, null, null, null, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(0, result.getTotalElements());
    assertTrue(result.getContent().isEmpty());
  }

  @Test
  @DisplayName("Test get all employees with only department filter")
  public void testGetAllEmployeesWithOnlyDepartmentFilter() {
    // Arrange
    String department = "HR";
    Pageable pageable = PageRequest.of(0, 10);

    Employee hrEmployee = new Employee();
    hrEmployee.setId(2L);
    hrEmployee.setName("Jane Smith");
    hrEmployee.setEmail("jane.smith@example.com");
    hrEmployee.setDepartment("HR");
    hrEmployee.setSalary(65000.0);
    hrEmployee.setJoiningDate(LocalDate.of(2023, 3, 1));

    EmployeeDTO hrEmployeeDTO = new EmployeeDTO();
    hrEmployeeDTO.setId(2L);
    hrEmployeeDTO.setName("Jane Smith");
    hrEmployeeDTO.setEmail("jane.smith@example.com");
    hrEmployeeDTO.setDepartment("HR");
    hrEmployeeDTO.setSalary(65000.0);
    hrEmployeeDTO.setJoiningDate(LocalDate.of(2023, 3, 1));

    List<Employee> employees = List.of(hrEmployee);
    Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

    when(employeeRepository.findAll(any(Specification.class), eq(pageable)))
        .thenReturn(employeePage);
    when(modelMapper.map(any(Employee.class), eq(EmployeeDTO.class))).thenReturn(hrEmployeeDTO);

    // Act
    Page<EmployeeDTO> result =
        employeeService.getAllEmployees(department, null, null, null, null, pageable);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(hrEmployeeDTO, result.getContent().get(0));

    // Verify specification was built correctly
    verify(employeeRepository).findAll(specCaptor.capture(), eq(pageable));
  }
}
