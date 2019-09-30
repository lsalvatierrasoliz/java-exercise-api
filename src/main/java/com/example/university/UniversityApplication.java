package com.example.university;

import com.example.university.domain.Course;
import com.example.university.domain.Student;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.StudentRepository;
import com.example.university.service.CourseRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class UniversityApplication implements CommandLineRunner {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRegistrationService courseRegistrationService;

    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        // create new students
        Student s1 = studentRepository.save(new Student("Luis", "Chavez"));
        Student s2 = studentRepository.save(new Student("Gerardo", "Arias"));
        Student s3 = studentRepository.save(new Student("Enrriqueta", "Olloa"));
        Student s4 = studentRepository.save(new Student("Fererico", "Gonzales"));
        Student s5 = studentRepository.save(new Student("Vilma", "Palma"));

        // create new courses
        Course c1 = courseRepository.save(new Course("BBC", "English", "English Course Advanced"));
        Course c2 = courseRepository.save(new Course("CC", "Computing", "Computing Course Advanced"));
        Course c3 = courseRepository.save(new Course("QA", "Quality Assurance", "Quality Assurance Course Advanced"));


        // set students for english
        courseRegistrationService.registerStudentToCourse(c1.getCode(), s1.getStudentId());
        courseRegistrationService.registerStudentToCourse(c1.getCode(), s2.getStudentId());
        courseRegistrationService.registerStudentToCourse(c1.getCode(), s3.getStudentId());

        // set students for computing
        courseRegistrationService.registerStudentToCourse(c2.getCode(), s3.getStudentId());
        courseRegistrationService.registerStudentToCourse(c2.getCode(), s4.getStudentId());
        courseRegistrationService.registerStudentToCourse(c2.getCode(), s5.getStudentId());

        // set students for quality assurance
        courseRegistrationService.registerStudentToCourse(c3.getCode(), s1.getStudentId());
        courseRegistrationService.registerStudentToCourse(c3.getCode(), s2.getStudentId());
        courseRegistrationService.registerStudentToCourse(c3.getCode(), s3.getStudentId());
        courseRegistrationService.registerStudentToCourse(c3.getCode(), s4.getStudentId());
        courseRegistrationService.registerStudentToCourse(c3.getCode(), s5.getStudentId());

    }
}
