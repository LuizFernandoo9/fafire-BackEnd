package com.project.professor_allocation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.professor_allocation.model.Allocation;
import com.project.professor_allocation.model.Course;
import com.project.professor_allocation.model.Professor;
import com.project.professor_allocation.repository.AllocationRepository;

@Service
public class AllocationService {

	private final AllocationRepository allocationRepository;
	private final ProfessorService professorService;
	private final CourseService courseService;

	public AllocationService(
			AllocationRepository allocationRepository,
			ProfessorService professorService,
			CourseService courseService) {

		this.allocationRepository = allocationRepository;
		this.professorService = professorService;
		this.courseService = courseService;
	}

	public List<Allocation> findAll() {
		return allocationRepository.findAll();
	}

	public Allocation findById(Long id) {
		return allocationRepository.findById(id).orElse(null);
	}

	public List<Allocation> findByProfessor(Long professorId) {
		Professor professor = new Professor();
		professor.setId(professorId);
		return allocationRepository.findByProfessor(professor);
	}

	public List<Allocation> findByCourse(Long courseId) {
		Course course = new Course();
		course.setId(courseId);
		return allocationRepository.findByCourse(course);
	}

	public Allocation save(Allocation allocation) {
		allocation.setId(null);
		return saveInternal(allocation);
	}

	public Allocation update(Allocation allocation) {
		Long id = allocation.getId();

		if (id == null || !allocationRepository.existsById(id)) {
			return null;
		}

		return saveInternal(allocation);
	}

	public void deleteById(Long id) {
		if (allocationRepository.existsById(id)) {
			allocationRepository.deleteById(id);
		}
	}

	private Allocation saveInternal(Allocation allocation) {
		if (!isEndHourGreaterThanStartHour(allocation)) {
			throw new IllegalArgumentException("O horário final deve ser maior que o horário inicial.");
		}

		if (allocation.getProfessor() == null || allocation.getProfessor().getId() == null) {
			throw new IllegalArgumentException("Professor inválido.");
		}

		if (allocation.getCourse() == null || allocation.getCourse().getId() == null) {
			throw new IllegalArgumentException("Curso inválido.");
		}

		Professor professor = professorService.findById(allocation.getProfessor().getId());
		if (professor == null) {
			throw new IllegalArgumentException("Professor não encontrado.");
		}

		Course course = courseService.findById(allocation.getCourse().getId());
		if (course == null) {
			throw new IllegalArgumentException("Curso não encontrado.");
		}

		if (hasCollision(allocation)) {
			throw new IllegalArgumentException("O professor já possui uma alocação nesse horário.");
		}

		allocation = allocationRepository.save(allocation);
		allocation.setCourse(course);
		allocation.setProfessor(professor);

		return allocation;
	}

	private boolean isEndHourGreaterThanStartHour(Allocation allocation) {
		return allocation.getStartHour() != null
				&& allocation.getEndHour() != null
				&& allocation.getEndHour().isAfter(allocation.getStartHour());
	}

	private boolean hasCollision(Allocation allocation) {
		List<Allocation> allocations = allocationRepository.findByProfessor(allocation.getProfessor());

		return allocations.stream()
				.filter(existing -> !existing.getId().equals(allocation.getId()))
				.anyMatch(existing -> hasCollision(existing, allocation));
	}

	private boolean hasCollision(Allocation currentAllocation, Allocation newAllocation) {
		return currentAllocation.getDayOfWeek() == newAllocation.getDayOfWeek()
				&& newAllocation.getStartHour().isBefore(currentAllocation.getEndHour())
				&& currentAllocation.getStartHour().isBefore(newAllocation.getEndHour());
	}
}
