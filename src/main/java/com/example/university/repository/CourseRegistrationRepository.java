package com.example.university.repository;

import com.example.university.domain.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {

    List<CourseRegistration> findByCourse_Code(String code);
    List<CourseRegistration> findByStudent_StudentId(Integer studentId);
    Optional<CourseRegistration> findByStudent_StudentIdAndCourse_Code(Integer studentId, String code);
}
