package com.brandsmashers.employee_api.repository;

import com.brandsmashers.employee_api.model.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, PagingAndSortingRepository<Student, Integer> {
    @Query("from Student")
    Page<Student> getAllStudents(Pageable p);
    Page<Student> findByAddress(String address,Pageable p);
}