package com.codesofscar.authentication.dto;

import com.codesofscar.authentication.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private Role role;
    private Long departmentId;
}

