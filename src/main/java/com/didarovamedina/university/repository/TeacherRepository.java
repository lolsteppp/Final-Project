package com.didarovamedina.university.repository;

import com.didarovamedina.university.entity.DidarovaMedinaTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<DidarovaMedinaTeacher, Long> {
    Optional<DidarovaMedinaTeacher> findByUserId(Long userId);
}
