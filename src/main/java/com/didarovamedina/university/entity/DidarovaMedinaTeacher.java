package com.didarovamedina.university.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "teachers")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DidarovaMedinaTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String firstName;
    @NotBlank private String lastName;
    private String department;

    @OneToOne
    @JoinColumn(name = "user_id")
    private DidarovaMedinaUser user;
}
