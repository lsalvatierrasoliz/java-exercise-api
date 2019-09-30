package com.example.university.search;

import com.example.university.domain.Course;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CourseContainsTitleSpec implements Specification<Course> {

    private final String title;

    public CourseContainsTitleSpec(String title) {
        this.title = title;
    }

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (title == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true)); // always true = no filtering
        }

        return criteriaBuilder.like(
                criteriaBuilder.lower(root.<String>get("title")), "%" + this.title.toLowerCase() + "%");
    }
}
