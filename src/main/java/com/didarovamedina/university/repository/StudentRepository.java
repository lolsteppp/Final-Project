package com.didarovamedina.university.repository;

import com.didarovamedina.university.entity.DidarovaMedinaStudent;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<DidarovaMedinaStudent, Long> {
    Page<DidarovaMedinaStudent> findByFirstNameContainingIgnoreCase(String name, Pageable p);
}