package com.example.university.rest;

import com.example.university.domain.Course;
import com.example.university.domain.Student;
import com.example.university.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Api(description = "API Course resource CRUD operations")
@RestController
@RequestMapping("/courses")
public class CourseResource {

    private CourseService courseService;

    @Autowired
    public CourseResource(CourseService courseService){
        this.courseService = courseService;
    }

    protected CourseResource() {
    }

    /**
     *  Lookup all courses
     *
     * @return
     */

    @GetMapping
    @ApiOperation("Get all courses in system")
    public ResponseEntity<Page<Course>> getAllCourses(@RequestParam Optional<String> title,
                                                      Pageable pageable) {

        Page<Course> courses = courseService.lookupCourses(title, pageable);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }


    /**
     * Get a course by code
     *
     * @param code
     * @return
     */

    @ApiOperation("Get course by code")
    @GetMapping("/{code}")
    public ResponseEntity<Course> getCourseBy(@PathVariable("code") final String code){

        return new ResponseEntity<>(courseService.lookupById(code).orElseThrow(
                () -> new NoSuchElementException("Student " + code + " not found")), HttpStatus.OK);
    }


    /**
     * Delete a course by code
     *
     * @param code
     * @return
     */
    @ApiOperation("Delete course by code")
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("code") final String code){

        courseService.delete(code);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Create a course
     *
     * @param course
     * @return
     */
    @ApiOperation("Create a new course")
    @PostMapping
    public ResponseEntity<Course> newCourse(@RequestBody Course course){

        Course newCourse = courseService.createNew(course);
        return new  ResponseEntity<>(newCourse ,HttpStatus.CREATED);

    }

    /**
     *  Update a course
     *
     * @param course
     * @return
     */
    @ApiOperation("Update a course by code")
    @PutMapping("/{code}")
    public ResponseEntity<Course> updateCourse(@PathVariable("code") final String code,  @RequestBody Course course){

        Course updatedCourse =  courseService.update(code, course);
        return new  ResponseEntity<>(updatedCourse ,HttpStatus.OK);
    }

    /**
     * Lookup all students from a course
     *
     * @param code
     * @return
     */
    @ApiOperation(value = "Get all Students assigned to a Course")
    @GetMapping("/{code}/students")
    public  ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable("code") final String code){

        List<Student> students =  courseService.getStudents(code);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
