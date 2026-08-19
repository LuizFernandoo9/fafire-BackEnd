package com.project.professor_allocation.dto;

import com.project.professor_allocation.model.Department;
import com.project.professor_allocation.model.Professor;

public class ProfessorDTO {
    private Long id;
    private String name;
    private String cpf;
    private Long departmentId;

    public ProfessorDTO() {
    }

    public ProfessorDTO(Long id, String name, String cpf, Long departmentId) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.departmentId = departmentId;
    }

    public static ProfessorDTO fromEntity(Professor professor) {
        if (professor == null) {
            return null;
        }

        Long departmentId = professor.getDepartment() != null ? professor.getDepartment().getId() : null;
        return new ProfessorDTO(professor.getId(), professor.getName(), professor.getCpf(), departmentId);
    }

    public static Professor toEntity(ProfessorDTO dto) {
        if (dto == null) {
            return null;
        }

        Professor professor = new Professor();
        professor.setId(dto.getId());
        professor.setName(dto.getName());
        professor.setCpf(dto.getCpf());

        if (dto.getDepartmentId() != null) {
            Department department = new Department();
            department.setId(dto.getDepartmentId());
            professor.setDepartment(department);
        }

        return professor;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
