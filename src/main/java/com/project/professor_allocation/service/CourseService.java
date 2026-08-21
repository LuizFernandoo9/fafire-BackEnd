package com.project.professor_allocation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.professor_allocation.model.Course;
import com.project.professor_allocation.repository.CourseRepository;

@Service
public class CourseService {

	private final CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public List<Course> findAll() {
		return courseRepository.findAll();
	}

	public Course findById(Long id) {
		return courseRepository.findById(id).orElse(null);
	}

	public Course save(Course course) {
		course.setId(null);
		return courseRepository.save(course);
	}

	public Course update(Course course) {
		Long id = course.getId();

		if (id == null || !courseRepository.existsById(id)) {
			return null;
		}

		return courseRepository.save(course);
	}

	public void deleteById(Long id) {
		if (courseRepository.existsById(id)) {
			courseRepository.deleteById(id);
		}
	}
}