package com.project.professor_allocation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.professor_allocation.dto.ProfessorDTO;
import com.project.professor_allocation.exception.ApiError;
import com.project.professor_allocation.model.Professor;
import com.project.professor_allocation.service.ProfessorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Professors")
@RestController
@RequestMapping(path = "/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        super();
        this.professorService = professorService;
    }

    @Operation(summary = "Find all professors")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProfessorDTO>> findAll(@RequestParam(name = "name", required = false) String name) {
        List<Professor> professors = (name == null) ? professorService.findAll() : professorService.findByName(name);
        List<ProfessorDTO> response = professors.stream().map(ProfessorDTO::fromEntity).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Find a professor")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "OK"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping(path = "/{professor_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable(name = "professor_id") Long id) {
        Professor professor = professorService.findById(id);
        if (professor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), "Professor não encontrado."));
        }

        return new ResponseEntity<>(ProfessorDTO.fromEntity(professor), HttpStatus.OK);
    }

    @Operation(summary = "Find professors by department")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "OK"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping(path = "/department/{department_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProfessorDTO>> findByDepartment(@PathVariable(name = "department_id") Long id) {
        List<Professor> professors = professorService.findByDepartment(id);
        List<ProfessorDTO> response = professors.stream().map(ProfessorDTO::fromEntity).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Save a professor")
    @ApiResponses({
    	@ApiResponse(responseCode = "201", description = "Created"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfessorDTO> save(@RequestBody ProfessorDTO professorDTO) {
        Professor professor = professorService.save(ProfessorDTO.toEntity(professorDTO));
        return new ResponseEntity<>(ProfessorDTO.fromEntity(professor), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a professor")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "OK"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping(path = "/{professor_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable(name = "professor_id") Long id,
                                            @RequestBody ProfessorDTO professorDTO) {
        professorDTO.setId(id);
        Professor professor = professorService.update(ProfessorDTO.toEntity(professorDTO));
        if (professor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), "Professor não encontrado."));
        }

        return new ResponseEntity<>(ProfessorDTO.fromEntity(professor), HttpStatus.OK);
    }

    @Operation(summary = "Delete a professor")
    @ApiResponses({
    	@ApiResponse(responseCode = "204", description = "No Content"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @DeleteMapping(path = "/{professor_id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "professor_id") Long id) {
        professorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
