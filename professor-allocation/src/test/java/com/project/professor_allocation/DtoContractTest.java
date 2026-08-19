package com.project.professor_allocation;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.project.professor_allocation.controller.ProfessorController;
import com.project.professor_allocation.model.Department;
import com.project.professor_allocation.model.Professor;
import com.project.professor_allocation.service.ProfessorService;

@WebMvcTest(controllers = ProfessorController.class)
class DtoContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfessorService professorService;

    @Test
    void professorResponseUsesDtoWithoutCircularReferences() throws Exception {
        Department department = new Department();
        department.setId(2L);
        department.setName("Computação");

        Professor professor = new Professor();
        professor.setId(1L);
        professor.setName("Ana Silva");
        professor.setCpf("12345678901");
        professor.setDepartment(department);

        when(professorService.findById(1L)).thenReturn(professor);

        mockMvc.perform(get("/professors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ana Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678901"))
                .andExpect(jsonPath("$.departmentId").value(2))
                .andExpect(jsonPath("$.department").doesNotExist());
    }

    @Test
    void notFoundReturnsErrorBody() throws Exception {
        when(professorService.findById(999L)).thenReturn(null);

        mockMvc.perform(get("/professors/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
    }
}
