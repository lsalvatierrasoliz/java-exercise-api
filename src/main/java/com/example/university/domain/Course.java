package com.example.university.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
@Table(name = "course")
public class Course {

    @NotNull
    @Id
    private String code;

    @NotNull
    @Column
    private String title;

    @Column(length = 2000)
    private String description;

    public Course(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    protected Course() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, title, description);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;

        Course other = (Course) obj;
        if(!Objects.equals(other.code, this.code)) return false;

        return true;
    }
}
