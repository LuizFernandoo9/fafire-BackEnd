package com.project.professor_allocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.professor_allocation.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
