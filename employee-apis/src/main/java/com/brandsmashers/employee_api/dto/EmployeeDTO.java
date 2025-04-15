package com.brandsmashers.employee_api.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String department;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Positive
    private Double salary;

    @NotNull
    private LocalDate joiningDate;
}
