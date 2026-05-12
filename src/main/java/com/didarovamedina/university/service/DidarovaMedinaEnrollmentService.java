package com.didarovamedina.university.service;

import com.didarovamedina.university.dto.EnrollmentDTO;
import com.didarovamedina.university.entity.*;
import com.didarovamedina.university.exception.ResourceNotFoundException;
import com.didarovamedina.university.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DidarovaMedinaEnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final DidarovaMedinaEmailService emailService;

    public EnrollmentDTO enroll(Long studentId, Long courseId) {
        DidarovaMedinaStudent student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        DidarovaMedinaCourse course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        DidarovaMedinaEnrollment e = DidarovaMedinaEnrollment.builder()
                .student(student).course(course)
                .enrolledAt(LocalDateTime.now()).build();
        DidarovaMedinaEnrollment saved = enrollmentRepository.save(e);
        emailService.sendEnrollmentEmail(student.getEmail(), student.getFirstName(), course.getTitle());
        log.info("Student {} enrolled in course {}", studentId, courseId);
        return toDTO(saved);
    }

    public List<EnrollmentDTO> getByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public EnrollmentDTO updateGrade(Long id, Double grade) {
        DidarovaMedinaEnrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        e.setGrade(grade);
        emailService.sendGradeNotification(e.getStudent().getEmail(), grade);
        log.info("Grade {} set for enrollment {}", grade, id);
        return toDTO(enrollmentRepository.save(e));
    }

    public void delete(Long id) { enrollmentRepository.deleteById(id); }

    private EnrollmentDTO toDTO(DidarovaMedinaEnrollment e) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setId(e.getId());
        dto.setStudentId(e.getStudent().getId());
        dto.setCourseId(e.getCourse().getId());
        dto.setEnrolledAt(e.getEnrolledAt());
        dto.setGrade(e.getGrade());
        return dto;
    }
}
