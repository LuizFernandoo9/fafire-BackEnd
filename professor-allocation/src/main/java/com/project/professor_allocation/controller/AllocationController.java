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
import org.springframework.web.bind.annotation.RestController;

import com.project.professor_allocation.dto.AllocationDTO;
import com.project.professor_allocation.exception.ApiError;
import com.project.professor_allocation.model.Allocation;
import com.project.professor_allocation.service.AllocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Allocations")
@RestController
@RequestMapping(path = "/allocations")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        super();
        this.allocationService = allocationService;
    }

    @Operation(summary = "Find all allocations")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AllocationDTO>> findAll() {
        List<Allocation> allocations = allocationService.findAll();
        List<AllocationDTO> response = allocations.stream().map(AllocationDTO::fromEntity).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Find an allocation")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "OK"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @GetMapping(path = "/{allocation_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findById(@PathVariable(name = "allocation_id") Long id) {
        Allocation allocation = allocationService.findById(id);
        if (allocation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), "Alocação não encontrada."));
        }

        return new ResponseEntity<>(AllocationDTO.fromEntity(allocation), HttpStatus.OK);
    }

    @Operation(summary = "Find allocations by professor")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "OK"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping(path = "/professor/{professor_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AllocationDTO>> findByProfessor(@PathVariable(name = "professor_id") Long id) {
        List<Allocation> allocations = allocationService.findByProfessor(id);
        List<AllocationDTO> response = allocations.stream().map(AllocationDTO::fromEntity).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Find allocations by course")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "OK"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @GetMapping(path = "/course/{course_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AllocationDTO>> findByCourse(@PathVariable(name = "course_id") Long id) {
        List<Allocation> allocations = allocationService.findByCourse(id);
        List<AllocationDTO> response = allocations.stream().map(AllocationDTO::fromEntity).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Save an allocation")
    @ApiResponses({
    	@ApiResponse(responseCode = "201", description = "Created"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AllocationDTO> save(@RequestBody AllocationDTO allocationDTO) {
        Allocation allocation = allocationService.save(AllocationDTO.toEntity(allocationDTO));
        return new ResponseEntity<>(AllocationDTO.fromEntity(allocation), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an allocation")
    @ApiResponses({
    	@ApiResponse(responseCode = "200", description = "OK"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content)
    })
    @PutMapping(path = "/{allocation_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable(name = "allocation_id") Long id,
                                             @RequestBody AllocationDTO allocationDTO) {
        allocationDTO.setId(id);
        Allocation allocation = allocationService.update(AllocationDTO.toEntity(allocationDTO));
        if (allocation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), "Alocação não encontrada."));
        }

        return new ResponseEntity<>(AllocationDTO.fromEntity(allocation), HttpStatus.OK);
    }

    @Operation(summary = "Delete an allocation")
    @ApiResponses({
    	@ApiResponse(responseCode = "204", description = "No Content"),
    	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    })
    @DeleteMapping(path = "/{allocation_id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "allocation_id") Long id) {
        allocationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
