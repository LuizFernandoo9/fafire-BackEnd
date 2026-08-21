package com.project.professor_allocation.dto;

import com.project.professor_allocation.model.Course;

import jakarta.validation.constraints.NotBlank;

public class CourseDTO {
    private Long id;

    @NotBlank(message = "Nome e obrigatorio.")
    private String name;

    public CourseDTO() {
    }

    public CourseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CourseDTO fromEntity(Course course) {
        if (course == null) {
            return null;
        }

        return new CourseDTO(course.getId(), course.getName());
    }

    public static Course toEntity(CourseDTO dto) {
        if (dto == null) {
            return null;
        }

        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        return course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
