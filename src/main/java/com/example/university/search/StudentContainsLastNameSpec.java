package com.example.university.search;

import com.example.university.domain.Student;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class StudentContainsLastNameSpec implements Specification<Student> {

    private final String lastName;

    public StudentContainsLastNameSpec(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (lastName == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true = no filtering
        }

        return criteriaBuilder.like(
                criteriaBuilder.lower(root.<String>get("lastName")), "%" + this.lastName.toLowerCase() + "%");
    }
}


