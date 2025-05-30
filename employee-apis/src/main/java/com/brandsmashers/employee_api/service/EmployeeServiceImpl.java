package com.brandsmashers.employee_api.service;

import com.brandsmashers.employee_api.dto.EmployeeDTO;
import com.brandsmashers.employee_api.exception.ResourceNotFoundException;
import com.brandsmashers.employee_api.model.Employee;
import com.brandsmashers.employee_api.repository.EmployeeRepository;
import com.brandsmashers.employee_api.specification.EmployeeSpecification;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final ModelMapper modelMapper;

  @Override
  public EmployeeDTO saveEmployee(Employee employee) {
    Employee saved = employeeRepository.save(employee);
    return modelMapper.map(saved, EmployeeDTO.class);
  }

  @Override
  public Optional<EmployeeDTO> getEmployeeById(Long id) {
    Optional<Employee> employee = employeeRepository.findById(id);
    return employee.map(emp -> modelMapper.map(emp, EmployeeDTO.class));
  }

  @Override
  public EmployeeDTO updateEmployee(Long id, Employee updated) {
    Employee existing =
        employeeRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

    existing.setName(updated.getName());
    existing.setDepartment(updated.getDepartment());
    existing.setEmail(updated.getEmail());
    existing.setSalary(updated.getSalary());
    existing.setJoiningDate(updated.getJoiningDate());

    Employee saved = employeeRepository.save(existing);
    return modelMapper.map(saved, EmployeeDTO.class);
  }

  @Override
  public void deleteEmployee(Long id) {
    if (!employeeRepository.existsById(id)) {
      throw new ResourceNotFoundException("Employee not found with ID: " + id);
    }
    employeeRepository.deleteById(id);
  }

  @Override
  public Page<EmployeeDTO> getAllEmployees(
      String department,
      Double minSalary,
      Double maxSalary,
      LocalDate joinedAfter,
      LocalDate joinedBefore,
      Pageable pageable) {

    Specification<Employee> spec = Specification.where(null);

    if (department != null) spec = spec.and(EmployeeSpecification.hasDepartment(department));

    if (minSalary != null) spec = spec.and(EmployeeSpecification.hasMinSalary(minSalary));

    if (maxSalary != null) spec = spec.and(EmployeeSpecification.hasMaxSalary(maxSalary));

    if (joinedAfter != null) spec = spec.and(EmployeeSpecification.joinedAfter(joinedAfter));

    if (joinedBefore != null) spec = spec.and(EmployeeSpecification.joinedBefore(joinedBefore));

    Page<Employee> employees = employeeRepository.findAll(spec, pageable);
    return employees.map(emp -> modelMapper.map(emp, EmployeeDTO.class));
  }

  @Override
  public void increaseSalary(String department, Double percent) {
    employeeRepository.incrementSalaryByDepartment(department, percent);
  }

  @Override
  public Double getTotalSalaryByDepartment(String department) {
    return employeeRepository.getTotalSalaryByDepartment(department);
  }

  @Override
  public byte[] generateEmployeePdf(EmployeeDTO employee) throws IOException {
    try (PDDocument document = new PDDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {

      PDPage page = new PDPage(PDRectangle.A4);
      document.addPage(page);

      PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
      PDType1Font fontNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

      try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
        float y = 750;

        contentStream.beginText();
        contentStream.setFont(fontBold, 18);
        contentStream.newLineAtOffset(50, y);
        contentStream.showText("Employee Details");
        contentStream.endText();

        y -= 40;

        contentStream.beginText();
        contentStream.setFont(fontNormal, 12);
        contentStream.newLineAtOffset(50, y);
        contentStream.showText("Name: " + employee.getName());
        contentStream.endText();

        y -= 20;

        contentStream.beginText();
        contentStream.setFont(fontNormal, 12);
        contentStream.newLineAtOffset(50, y);
        contentStream.showText("Department: " + employee.getDepartment());
        contentStream.endText();

        y -= 20;

        contentStream.beginText();
        contentStream.setFont(fontNormal, 12);
        contentStream.newLineAtOffset(50, y);
        contentStream.showText("Joining Date: " + employee.getJoiningDate());
        contentStream.endText();

        y -= 20;

        contentStream.beginText();
        contentStream.setFont(fontNormal, 12);
        contentStream.newLineAtOffset(50, y);
        contentStream.showText("Email: " + employee.getEmail());
        contentStream.endText();
      }

      document.save(out);
      return out.toByteArray();
    }
  }
}
