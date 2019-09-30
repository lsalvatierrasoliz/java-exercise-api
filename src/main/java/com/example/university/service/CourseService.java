package com.example.university.service;

import com.example.university.domain.Course;
import com.example.university.domain.Student;
import com.example.university.repository.CourseRepository;
import com.example.university.search.CourseContainsTitleSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository courseRepository;
    private CourseRegistrationService courseRegistrationService;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseRegistrationService courseRegistrationService) {
        this.courseRepository = courseRepository;
        this.courseRegistrationService = courseRegistrationService;
    }


    public Course update(final String codeCourse, Course course) throws NoSuchElementException{

        Course courseDb = validateCourse(codeCourse);
        courseDb.setDescription(course.getDescription());
        courseDb.setTitle(course.getTitle());

        return courseRepository.save(courseDb);
    }

    @Transactional
    public void delete(final String codeCourse) throws NoSuchElementException {

        Course course = validateCourse(codeCourse);

        courseRegistrationService.deleteRegistrationsByCourse(codeCourse);
        courseRepository.delete(course);
    }

    public Course createNew(Course course){
        return courseRepository.save(course);
    }

    public Optional<Course> lookupById(String codeCourse){
        return courseRepository.findById(codeCourse);
    }

    public List<Course> lookupAll(){
        return courseRepository.findAll();
    }

    public Page<Course> lookupCourses(Optional<String> title, Pageable pageable){
        Specification<Course> spec = new CourseContainsTitleSpec(title.orElse(null));
        return courseRepository.findAll(spec, pageable);
    }

    public List<Student> getStudents(final String codeCourse){
        return courseRegistrationService.getStudentsByCourse(codeCourse);
    }

    /**
     * Verify and return the Course given a code.
     *
     * @param codeCourse
     * @return
     * @throws NoSuchElementException
     */
    private Course validateCourse(String codeCourse) throws NoSuchElementException {
        return courseRepository.findById(codeCourse).orElseThrow(
                () -> new NoSuchElementException("Course " + codeCourse + " not found")
        );
    }

}
