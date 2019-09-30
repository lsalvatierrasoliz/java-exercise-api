package com.example.university.service;

import com.example.university.domain.Course;
import com.example.university.domain.Student;
import com.example.university.repository.StudentRepository;
import com.example.university.search.StudentContainsFirstNameSpec;
import com.example.university.search.StudentContainsLastNameSpec;
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
public class StudentService {

    private StudentRepository studentRepository;
    private CourseRegistrationService courseRegistrationService;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          CourseRegistrationService courseRegistrationService) {
        this.studentRepository = studentRepository;
        this.courseRegistrationService = courseRegistrationService;
    }

    public Student createNew(Student student){
        return studentRepository.save(student);
    }

    public Page<Student> lookupStudents(Optional<String> firstName, Optional<String> lastName, Pageable pageable){
        Specification<Student> spec = new StudentContainsFirstNameSpec(firstName.orElse(null)).
                and(new StudentContainsLastNameSpec(lastName.orElse(null)));

        return studentRepository.findAll(spec, pageable);
    }

    public List<Student> lookupAll(){
        return studentRepository.findAll();
    }

    public Optional<Student> lookupById(Integer studentId){
        return studentRepository.findById(studentId);
    }

    public Student update(Integer studentId, Student student){
        Student studentDb = validateStudent(studentId);

        studentDb.setFirstName(student.getFirstName());
        studentDb.setLastName(student.getLastName());

       return studentRepository.save(studentDb);
    }


    @Transactional
    public void delete(final Integer studentId){

        Student student = validateStudent(studentId);

        courseRegistrationService.deleteRegistrationsByStudent(studentId);
        studentRepository.delete(student);
    }

    public List<Course> getCourses(final Integer studentId){
        return courseRegistrationService.getCoursesByStudent(studentId);
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
