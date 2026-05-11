package com.didarovamedina.university.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeacherRequestDTO {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    private String department;
}