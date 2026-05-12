package com.didarovamedina.university.controller;

import com.didarovamedina.university.dto.*;
import com.didarovamedina.university.service.DidarovaMedinaStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
public class DidarovaMedinaStudentController {

    private final DidarovaMedinaStudentService studentService;

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String search) {
        log.info("GET /students page={} search={}", page, search);
        return ResponseEntity.ok(studentService.getAll(page, size, sortBy, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@Valid @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.status(201).body(studentService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable Long id,
                                             @Valid @RequestBody StudentRequestDTO dto) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}