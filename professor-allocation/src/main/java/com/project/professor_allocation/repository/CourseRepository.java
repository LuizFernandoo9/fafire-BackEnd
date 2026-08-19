package com.project.professor_allocation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.professor_allocation.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}