package com.didarovamedina.university.controller;

import com.didarovamedina.university.dto.*;
import com.didarovamedina.university.service.DidarovaMedinaCourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class DidarovaMedinaCourseController {

    private final DidarovaMedinaCourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer credits) {
        return ResponseEntity.ok(courseService.getAll(title, credits));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<CourseDTO> create(@Valid @RequestBody CourseRequestDTO dto) {
        return ResponseEntity.status(201).body(courseService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<CourseDTO> update(@PathVariable Long id,
                                            @Valid @RequestBody CourseRequestDTO dto) {
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}