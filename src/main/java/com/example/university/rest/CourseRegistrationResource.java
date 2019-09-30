package com.example.university.rest;

import com.example.university.domain.CourseRegistration;
import com.example.university.dto.StudentCourseDTO;
import com.example.university.exception.StudentAlreadyRegistered;
import com.example.university.service.CourseRegistrationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/courses/{code}/students")
public class CourseRegistrationResource {

    CourseRegistrationService courseRegistrationService;

    @Autowired
    public CourseRegistrationResource(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    @ApiOperation("Register Student to a Course")
    @PostMapping
    public ResponseEntity<StudentCourseDTO> registerStudentToCourse(@PathVariable("code") final String code, @RequestBody StudentCourseDTO modelDto) throws StudentAlreadyRegistered {

        CourseRegistration courseRegistration = courseRegistrationService.registerStudentToCourse(code, modelDto.getStudentId());
        StudentCourseDTO studentCourseDTO = new StudentCourseDTO();
        studentCourseDTO.setCourseCode(courseRegistration.getCourse().getCode());
        studentCourseDTO.setStudentId(courseRegistration.getStudent().getStudentId());
        studentCourseDTO.setFirstName(courseRegistration.getStudent().getFirstName());
        studentCourseDTO.setLastName(courseRegistration.getStudent().getLastName());


        return new ResponseEntity<>(studentCourseDTO, HttpStatus.CREATED);

    }

    @ApiOperation("Unregister Student from Course")
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> unregisterStudentFromCourse(@PathVariable("code") final String code, @PathVariable("studentId") Integer  studentId) throws NoSuchElementException {

        courseRegistrationService.unregisterStudentFromCourse(code, studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}