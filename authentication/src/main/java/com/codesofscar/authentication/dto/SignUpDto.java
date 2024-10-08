package com.codesofscar.authentication.dto;

import com.codesofscar.authentication.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "ErrorResponse",
        description = "Schema to sign up employee"
)
public class SignUpDto {
    @Size(min = 3, message = "First name must be at least 3 characters")
    private String firstName;
    @Size(min = 3, message = "Last name must be at least 3 characters")
    private String lastName;
    @Email(message = "*Entry must be an email address")
    @NotEmpty(message = "*Enter your valid email address")
    private String email;
    private String address;
    private Role role;
    @NotEmpty
    private String phoneNumber;
    @Pattern(regexp = "^.*(?=.{8,})(?=...*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "*Enter at least one uppercase,lowercase,digit and special character and minimum 8 characters")
    private String password;
    @Pattern(regexp = "^.*(?=.{8,})(?=...*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "*Enter at least one uppercase,lowercase,digit and special character and minimum 8 characters")
    private String confirmPassword;



}
