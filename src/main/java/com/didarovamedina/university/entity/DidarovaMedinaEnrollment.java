package com.didarovamedina.university.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DidarovaMedinaEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private DidarovaMedinaStudent student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private DidarovaMedinaCourse course;

    private LocalDateTime enrolledAt;
    private Double grade;
}