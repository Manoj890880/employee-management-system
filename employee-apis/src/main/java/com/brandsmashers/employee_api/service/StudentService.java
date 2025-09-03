package com.brandsmashers.employee_api.service;

import com.brandsmashers.employee_api.model.Student;

import java.util.List;

public interface StudentService {
    public Student addStudent(Student student);
    public List<Student> getSortedStudentListWithField(String field, String direction);
    public List<Student> getAllStudentsPageWise(Integer pageNumber, Integer numberOfRecords);

}