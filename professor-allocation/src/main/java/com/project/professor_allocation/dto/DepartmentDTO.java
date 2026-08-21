package com.project.professor_allocation.dto;

import com.project.professor_allocation.model.Department;

import jakarta.validation.constraints.NotBlank;

public class DepartmentDTO {
    private Long id;

    @NotBlank(message = "Nome e obrigatorio.")
    private String name;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DepartmentDTO fromEntity(Department department) {
        if (department == null) {
            return null;
        }

        return new DepartmentDTO(department.getId(), department.getName());
    }

    public static Department toEntity(DepartmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Department department = new Department();
        department.setId(dto.getId());
        department.setName(dto.getName());
        return department;
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
