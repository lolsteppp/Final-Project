package com.didarovamedina.university.controller;

import com.didarovamedina.university.dto.EnrollmentDTO;
import com.didarovamedina.university.service.DidarovaMedinaEnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Slf4j
public class DidarovaMedinaEnrollmentController {

    private final DidarovaMedinaEnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentDTO> enroll(
            @RequestParam Long studentId,
            @RequestParam Long courseId) {
        return ResponseEntity.status(201).body(enrollmentService.enroll(studentId, courseId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDTO>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getByStudent(studentId));
    }

    @PutMapping("/{id}/grade")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<EnrollmentDTO> updateGrade(@PathVariable Long id,
                                                     @RequestParam Double grade) {
        return ResponseEntity.ok(enrollmentService.updateGrade(id, grade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}