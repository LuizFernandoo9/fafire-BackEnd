package com.project.professor_allocation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.professor_allocation.model.Department;
import com.project.professor_allocation.repository.DepartmentRepository;

@Service
public class DepartmentService {

	private final DepartmentRepository departmentRepository;

	public DepartmentService(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

	public Department findById(Long id) {
		return departmentRepository.findById(id).orElse(null);
	}

	public Department save(Department department) {
		department.setId(null);
		return departmentRepository.save(department);
	}

	public Department update(Department department) {
		Long id = department.getId();

		if (id == null || !departmentRepository.existsById(id)) {
			return null;
		}

		return departmentRepository.save(department);
	}

	public void deleteById(Long id) {
		if (departmentRepository.existsById(id)) {
			departmentRepository.deleteById(id);
		}
	}
}
