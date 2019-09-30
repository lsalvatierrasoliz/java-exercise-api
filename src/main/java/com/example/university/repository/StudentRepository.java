package com.example.university.repository;

import com.example.university.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

    /*@Query("select student from Student student left join fetch student.courses where student.studentId =:id")
    Optional<Student> findByIdAndFetchCourses(@Param("id") Integer studentId);*/
}
