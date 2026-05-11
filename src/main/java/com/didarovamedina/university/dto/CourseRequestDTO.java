package com.didarovamedina.university.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CourseRequestDTO {
    @NotBlank private String title;
    private String description;
    private Integer credits;
    private Long teacherId;
}