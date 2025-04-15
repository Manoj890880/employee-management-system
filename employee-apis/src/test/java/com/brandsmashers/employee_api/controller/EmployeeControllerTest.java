package com.brandsmashers.employee_api.controller;

import com.brandsmashers.employee_api.dto.EmployeeDTO;
import com.brandsmashers.employee_api.model.Employee;
import com.brandsmashers.employee_api.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDTO employeeDTO;
    private Employee employee;

    @BeforeEach
    public void setup() {
        // Create sample employee data for tests
        employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1L);
        employeeDTO.setName("John Doe");
        employeeDTO.setEmail("john.doe@example.com");
        employeeDTO.setDepartment("IT");
        employeeDTO.setSalary(75000.0);
        employeeDTO.setJoiningDate(LocalDate.of(2023, 1, 15));

        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");
        employee.setEmail("john.doe@example.com");
        employee.setDepartment("IT");
        employee.setSalary(75000.0);
        employee.setJoiningDate(LocalDate.of(2023, 1, 15));
    }

    @Test
    @DisplayName("Test create employee - success")
    public void testCreateEmployee() {
        // Arrange
        when(modelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenReturn(employee);
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.createEmployee(employeeDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDTO, response.getBody());
        verify(employeeService, times(1)).saveEmployee(employee);
    }

    @Test
    @DisplayName("Test get employee by ID - found")
    public void testGetEmployeeById_Found() {
        // Arrange
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employeeDTO));

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDTO, response.getBody());
    }

    @Test
    @DisplayName("Test get employee by ID - not found")
    public void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeService.getEmployeeById(99L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Test update employee - success")
    public void testUpdateEmployee() {
        // Arrange
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setId(1L);
        updatedEmployeeDTO.setName("John Updated");
        updatedEmployeeDTO.setEmail("john.updated@example.com");
        updatedEmployeeDTO.setDepartment("Management");
        updatedEmployeeDTO.setSalary(85000.0);
        updatedEmployeeDTO.setJoiningDate(LocalDate.of(2023, 1, 15));

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        updatedEmployee.setName("John Updated");
        updatedEmployee.setEmail("john.updated@example.com");
        updatedEmployee.setDepartment("Management");
        updatedEmployee.setSalary(85000.0);
        updatedEmployee.setJoiningDate(LocalDate.of(2023, 1, 15));

        when(modelMapper.map(any(EmployeeDTO.class), eq(Employee.class))).thenReturn(updatedEmployee);
        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(updatedEmployeeDTO);

        // Act
        ResponseEntity<EmployeeDTO> response = employeeController.updateEmployee(1L, updatedEmployeeDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEmployeeDTO, response.getBody());
        verify(employeeService, times(1)).updateEmployee(eq(1L), any(Employee.class));
    }

    @Test
    @DisplayName("Test delete employee - success")
    public void testDeleteEmployee() {
        // Arrange
        doNothing().when(employeeService).deleteEmployee(1L);

        // Act
        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    @DisplayName("Test get all employees with filters")
    public void testGetAllEmployeesWithFilters() {
        // Arrange
        List<EmployeeDTO> employeesDTO = Arrays.asList(
                employeeDTO,
                createEmployeeDTO(2L, "Jane Smith", "HR", "jane.smith@example.com", 65000.0, LocalDate.of(2023, 3, 1))
        );

        Page<EmployeeDTO> page = new PageImpl<>(employeesDTO);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

        String department = "IT";
        Double minSalary = 50000.0;
        Double maxSalary = 100000.0;
        LocalDate joinedAfter = LocalDate.of(2023, 1, 1);
        LocalDate joinedBefore = LocalDate.of(2023, 12, 31);

        when(employeeService.getAllEmployees(
                eq(department),
                eq(minSalary),
                eq(maxSalary),
                eq(joinedAfter),
                eq(joinedBefore),
                eq(pageable)
        )).thenReturn(page);

        // Act
        ResponseEntity<Page<EmployeeDTO>> response = employeeController.getAllEmployees(
                department,
                minSalary,
                maxSalary,
                joinedAfter,
                joinedBefore,
                0,
                10,
                new String[]{"id", "asc"}
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        assertEquals(2, response.getBody().getContent().size());
        verify(employeeService).getAllEmployees(
                eq(department),
                eq(minSalary),
                eq(maxSalary),
                eq(joinedAfter),
                eq(joinedBefore),
                any(Pageable.class)
        );
    }

    @Test
    @DisplayName("Test get all employees without filters")
    public void testGetAllEmployeesWithoutFilters() {
        // Arrange
        List<EmployeeDTO> employeesDTO = Arrays.asList(employeeDTO);
        Page<EmployeeDTO> page = new PageImpl<>(employeesDTO);

        when(employeeService.getAllEmployees(
                eq(null),
                eq(null),
                eq(null),
                eq(null),
                eq(null),
                any(Pageable.class)
        )).thenReturn(page);

        // Act
        ResponseEntity<Page<EmployeeDTO>> response = employeeController.getAllEmployees(
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                new String[]{"id", "asc"}
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    @DisplayName("Test get all employees with descending sort")
    public void testGetAllEmployeesWithDescendingSort() {
        // Arrange
        List<EmployeeDTO> employeesDTO = Arrays.asList(employeeDTO);
        Page<EmployeeDTO> page = new PageImpl<>(employeesDTO);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "salary"));

        when(employeeService.getAllEmployees(
                eq(null),
                eq(null),
                eq(null),
                eq(null),
                eq(null),
                any(Pageable.class)
        )).thenReturn(page);

        // Act
        ResponseEntity<Page<EmployeeDTO>> response = employeeController.getAllEmployees(
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                new String[]{"salary", "desc"}
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(page, response.getBody());
        verify(employeeService).getAllEmployees(
                eq(null),
                eq(null),
                eq(null),
                eq(null),
                eq(null),
                any(Pageable.class)
        );
    }

    // Helper method to create employee DTOs for testing
    private EmployeeDTO createEmployeeDTO(Long id, String name, String department, String email,
                                          Double salary, LocalDate joiningDate) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDepartment(department);
        dto.setEmail(email);
        dto.setSalary(salary);
        dto.setJoiningDate(joiningDate);
        return dto;
    }
}