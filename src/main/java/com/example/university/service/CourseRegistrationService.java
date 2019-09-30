package com.example.university.service;

import com.example.university.domain.Course;
import com.example.university.domain.CourseRegistration;
import com.example.university.domain.Student;
import com.example.university.exception.StudentAlreadyRegistered;
import com.example.university.repository.CourseRegistrationRepository;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CourseRegistrationService {

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    public CourseRegistrationService(CourseRepository courseRepository, StudentRepository studentRepository,
                                     CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    public CourseRegistration registerStudentToCourse(final String codeCourse, final Integer studentId) throws StudentAlreadyRegistered, NoSuchElementException {

        Course course = validateCourse(codeCourse);
        Student student = validateStudent(studentId);

        if(courseRegistrationRepository.findByStudent_StudentIdAndCourse_Code(studentId, codeCourse).isPresent()){
            throw new StudentAlreadyRegistered("Student : " + studentId + " is already registered at course : " + codeCourse);
        }


        CourseRegistration courseRegistration =  new CourseRegistration(course, student);
        return courseRegistrationRepository.save(courseRegistration);
    }

    public void unregisterStudentFromCourse(final String codeCourse, final Integer studentId) throws NoSuchElementException {

        validateCourse(codeCourse);
        validateStudent(studentId);

        courseRegistrationRepository.findByStudent_StudentIdAndCourse_Code(studentId, codeCourse).ifPresent(cr ->
                courseRegistrationRepository.delete(cr)
        );
    }

    @Transactional
    public void deleteRegistrationsByStudent(final Integer studentId){

        List<CourseRegistration> courseRegistrations = courseRegistrationRepository.findByStudent_StudentId(studentId);
        courseRegistrationRepository.deleteInBatch(courseRegistrations);
    }

    @Transactional
    public void deleteRegistrationsByCourse(final String codeCourse){

        List<CourseRegistration> courseRegistrations = courseRegistrationRepository.findByCourse_Code(codeCourse);
        courseRegistrationRepository.deleteInBatch(courseRegistrations);
    }

    public List<Student> getStudentsByCourse(final String codeCourse) {

        List<CourseRegistration> courseRegistrations = courseRegistrationRepository.findByCourse_Code(codeCourse);
        return courseRegistrations.stream().map( cr -> cr.getStudent()).collect(Collectors.toList());
    }

    public List<Course> getCoursesByStudent(final Integer studentId)  {

        List<CourseRegistration> courseRegistrations = courseRegistrationRepository.findByStudent_StudentId(studentId);
        return courseRegistrations.stream().map( cr -> cr.getCourse()).collect(Collectors.toList());
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

    /**
     * Verify and return the Student given a studentId.
     *
     * @param studentId
     * @return
     * @throws NoSuchElementException
     */
    private Student validateStudent(Integer studentId) throws NoSuchElementException {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new NoSuchElementException("Student " + studentId + " not found")
        );
    }
}
