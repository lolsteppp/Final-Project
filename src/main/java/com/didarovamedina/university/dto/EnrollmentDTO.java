package com.didarovamedina.university.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EnrollmentDTO {
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDateTime enrolledAt;
    private Double grade;
}