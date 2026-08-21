package com.project.professor_allocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.project.professor_allocation.model.Professor;
import com.project.professor_allocation.model.Department;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	List<Professor> findByNameContainingIgnoreCase(String name);
	
	List<Professor> findByDepartment(Department department);
}