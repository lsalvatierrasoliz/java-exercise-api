package com.example.university.repository;

import com.example.university.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {

    /*@Query("select course from Course course left join fetch course.students where course.code =:code")
    Optional<Course> findByCodeAndFetchStudents(@Param("code") String code);*/
}
