package com.didarovamedina.university.repository;

import com.didarovamedina.university.entity.DidarovaMedinaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<DidarovaMedinaUser, Long> {
    Optional<DidarovaMedinaUser> findByEmail(String email);
}