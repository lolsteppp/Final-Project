package com.didarovamedina.university.repository;

import com.didarovamedina.university.entity.DidarovaMedinaEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<DidarovaMedinaEnrollment, Long> {
    List<DidarovaMedinaEnrollment> findByStudentId(Long studentId);
}