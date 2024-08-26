package com.codesofscar.employee_mgmt.dto;


import com.codesofscar.employee_mgmt.enums.Role;
import lombok.Data;

@Data
public class EmployeesDTO {
    private Long userId;
    private Long departmentId;
}

