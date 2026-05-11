package com.didarovamedina.university.dto;

import lombok.*;

@Data @Builder
public class TeacherDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String department;
}