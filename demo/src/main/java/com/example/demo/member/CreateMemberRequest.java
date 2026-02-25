package com.example.demo.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateMemberRequest(
        @NotBlank String name,
        @NotBlank @Email String email
) {
}
