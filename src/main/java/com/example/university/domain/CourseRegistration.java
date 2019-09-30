package com.example.university.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_registration")
public class CourseRegistration {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "code", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false)
    private Student student;

    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime registeredAt;

    public CourseRegistration(Course course, Student student) {
        this.course = course;
        this.student = student;
        this.registeredAt = LocalDateTime.now();
    }

    protected CourseRegistration() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}
