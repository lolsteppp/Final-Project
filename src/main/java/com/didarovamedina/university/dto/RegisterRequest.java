package com.didarovamedina.university.dto;

import com.didarovamedina.university.entity.DidarovaMedinaUser;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email @NotBlank private String email;
    @NotBlank private String password;
    @NotNull private DidarovaMedinaUser.Role role;
}