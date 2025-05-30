package com.brandsmashers.employee_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedStoredProcedureQuery(
    name = "incrementSalaryByDepartment",
    procedureName = "increment_salary_by_department",
    parameters = {
      @StoredProcedureParameter(mode = ParameterMode.IN, name = "dept_name", type = String.class),
      @StoredProcedureParameter(
          mode = ParameterMode.IN,
          name = "increment_percent",
          type = Double.class)
    })
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Department is required")
  private String department;

  @Email(message = "Invalid email")
  @NotBlank(message = "Email is required")
  private String email;

  @NotNull(message = "Salary is required")
  private Double salary;

  @NotNull(message = "Joining Date is required")
  private LocalDate joiningDate;
}
