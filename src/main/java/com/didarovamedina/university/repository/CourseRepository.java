package com.didarovamedina.university.repository;

import com.didarovamedina.university.entity.DidarovaMedinaCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<DidarovaMedinaCourse, Long> {
    List<DidarovaMedinaCourse> findByTitleContainingIgnoreCase(String title);
    List<DidarovaMedinaCourse> findByCredits(Integer credits);
}