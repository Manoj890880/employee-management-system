package com.brandsmashers.employee_api.service;


import java.util.List;

import com.brandsmashers.employee_api.model.Student;
import com.brandsmashers.employee_api.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository sRepo;

    @Override
    public Student addStudent(Student student) {
        return sRepo.save(student);
    }

    @Override
    public List<Student> getSortedStudentListWithField(String field, String direction) {
        Sort sortByFieldWithDirection = direction.equals("asc") ? Sort.by(field).ascending() : Sort.by(field).descending();
        return sRepo.findAll(sortByFieldWithDirection);
    }

    @Override
    public List<Student> getAllStudentsPageWise(Integer pageNumber, Integer numberOfRecords) {
        Pageable p = PageRequest.of(pageNumber-1, numberOfRecords);
//using method of PagingAndSortingRepository
//Page<Student> page= sRepo.findAll(p);
//using JPQL
//Page<Student> page= sRepo.getAllStudents(p);
//List<Student> students= page.getContent();
        Page<Student> page= sRepo.findAll(p);
        List<Student> students= page.getContent();
        System.out.println("students "+students);
        long totalRecords = page.getTotalElements();
        System.out.println("Total number of records: " + totalRecords);
        return students;
    }
}
