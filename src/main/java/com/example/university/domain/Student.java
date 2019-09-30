package com.example.university.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue
    private Integer studentId;

    @NotNull
    @Column(length = 200)
    private String firstName;

    @NotNull
    @Column(length = 200)
    private String lastName;

    public Student(@NotNull String firstName, @NotNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    protected Student() {
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;

        Student other = (Student) obj;
        if(!Objects.equals(other.studentId, this.studentId)) return false;

        return true;
    }
}
