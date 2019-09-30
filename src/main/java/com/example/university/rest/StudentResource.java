package com.example.university.rest;


import com.example.university.domain.Course;
import com.example.university.domain.Student;
import com.example.university.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Api(description = "API for CRUD operations Student Resource")
@RestController
@RequestMapping("/students")
public class StudentResource {

    private StudentService studentService;


    @Autowired
    public StudentResource(StudentService studentService) {
        this.studentService = studentService;
    }

    protected StudentResource() {
    }

    /**
     * Lookup all Students
     *
     * @return
     */
    @GetMapping
    @ApiOperation("Get All Students from system")
    public ResponseEntity<Page<Student>> getAllStudents(@RequestParam Optional<String> firstName,
                                                        @RequestParam Optional<String> lastName,
                                                        Pageable pageable) {

        Page<Student> students = studentService.lookupStudents(firstName, lastName, pageable);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }


    /**
     * Get Student by Id
     *
     * @param studentId
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Find Student by Id")
    public ResponseEntity<Student> getStudentBy(@PathVariable("id") final Integer studentId){

        return new ResponseEntity<>(studentService.lookupById(studentId).orElseThrow(
                () -> new NoSuchElementException("Student " + studentId + " not found")), HttpStatus.OK);
    }

    /**
     * Delete student by id
     *
     * @param studentId
     * @return
     */

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Student by Id")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") final Integer studentId){

        studentService.delete(studentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Create a Student
     *
     * @param student
     * @return
     */
    @ApiOperation(value = "Create new Student")
    @PostMapping
    public ResponseEntity<Student> newStudent(@RequestBody @Valid Student student){

        Student newStudent = studentService.createNew(student);

        return new  ResponseEntity<>(newStudent ,HttpStatus.CREATED);

    }

    /**
     * Update a Student
     *
     * @param student
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Update Student by Id")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") final Integer studentId, @RequestBody Student student){

        Student updatedStudent = studentService.update(studentId, student);
        return new  ResponseEntity<>(updatedStudent ,HttpStatus.OK);

    }

    /**
     * Lookup all courses from a Student
     *
     * @param studentId
     * @return
     */
    @ApiOperation(value = "Get all courses assigned to a Student")
    @GetMapping("/{id}/courses")
    public  ResponseEntity<List<Course>> getCoursesByStudent(@PathVariable("id") final Integer studentId){

        List<Course> courses = studentService.getCourses(studentId);
        return new ResponseEntity<>(courses, HttpStatus.OK);

    }
}
