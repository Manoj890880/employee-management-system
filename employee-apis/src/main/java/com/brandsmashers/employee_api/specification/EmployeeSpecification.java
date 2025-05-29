package com.brandsmashers.employee_api.specification;

import com.brandsmashers.employee_api.model.Employee;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

  public static Specification<Employee> hasDepartment(String department) {
    return (root, query, cb) -> cb.equal(root.get("department"), department);
  }

  public static Specification<Employee> hasMinSalary(Double minSalary) {
    return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("salary"), minSalary);
  }

  public static Specification<Employee> hasMaxSalary(Double maxSalary) {
    return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("salary"), maxSalary);
  }

  public static Specification<Employee> joinedAfter(LocalDate date) {
    return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("joiningDate"), date);
  }

  public static Specification<Employee> joinedBefore(LocalDate date) {
    return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("joiningDate"), date);
  }
}
