package com.brandsmashers.employee_api.repository;

import com.brandsmashers.employee_api.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository
    extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

  @Procedure(name = "incrementSalaryByDepartment")
  void incrementSalaryByDepartment(
      @Param("dept_name") String department, @Param("increment_percent") Double percent);

  @Query(value = "SELECT get_total_salary_by_department(:dept_name)", nativeQuery = true)
  Double getTotalSalaryByDepartment(@Param("dept_name") String department);
}
