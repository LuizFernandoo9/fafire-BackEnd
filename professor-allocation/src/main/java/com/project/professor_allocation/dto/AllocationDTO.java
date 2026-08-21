package com.project.professor_allocation.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.project.professor_allocation.model.Allocation;
import com.project.professor_allocation.model.Course;
import com.project.professor_allocation.model.Professor;

public class AllocationDTO {
    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime startHour;
    private LocalTime endHour;
    private Long professorId;
    private Long courseId;

    public AllocationDTO() {
    }

    public AllocationDTO(Long id, DayOfWeek dayOfWeek, LocalTime startHour, LocalTime endHour, Long professorId, Long courseId) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.endHour = endHour;
        this.professorId = professorId;
        this.courseId = courseId;
    }

    public static AllocationDTO fromEntity(Allocation allocation) {
        if (allocation == null) {
            return null;
        }

        Long professorId = allocation.getProfessor() != null ? allocation.getProfessor().getId() : null;
        Long courseId = allocation.getCourse() != null ? allocation.getCourse().getId() : null;

        return new AllocationDTO(
                allocation.getId(),
                allocation.getDayOfWeek(),
                allocation.getStartHour(),
                allocation.getEndHour(),
                professorId,
                courseId
        );
    }

    public static Allocation toEntity(AllocationDTO dto) {
        if (dto == null) {
            return null;
        }

        Allocation allocation = new Allocation();
        allocation.setId(dto.getId());
        allocation.setDayOfWeek(dto.getDayOfWeek());
        allocation.setStartHour(dto.getStartHour());
        allocation.setEndHour(dto.getEndHour());

        if (dto.getProfessorId() != null) {
            Professor professor = new Professor();
            professor.setId(dto.getProfessorId());
            allocation.setProfessor(professor);
        }

        if (dto.getCourseId() != null) {
            Course course = new Course();
            course.setId(dto.getCourseId());
            allocation.setCourse(course);
        }

        return allocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
