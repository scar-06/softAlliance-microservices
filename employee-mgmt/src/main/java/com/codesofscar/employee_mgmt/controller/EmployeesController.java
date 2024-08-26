package com.codesofscar.employee_mgmt.controller;

import com.codesofscar.employee_mgmt.dto.EmployeesDTO;
import com.codesofscar.employee_mgmt.dto.ResponseDTO;
import com.codesofscar.employee_mgmt.dto.UserDto;
import com.codesofscar.employee_mgmt.service.impl.DepartmentServiceImpl;
import com.codesofscar.employee_mgmt.service.impl.EmployeesServiceImpl;
import com.codesofscar.employee_mgmt.constants.EmployeeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeesController {
    private EmployeesServiceImpl userService;
    private DepartmentServiceImpl departmentService;

    @Autowired
    public EmployeesController(EmployeesServiceImpl userService, DepartmentServiceImpl departmentService) {
        this.userService = userService;
        this.departmentService = departmentService;
    }

    @PutMapping("/update-employee/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateEmployee(@RequestBody UserDto employeeDTO,
                                                      @PathVariable Long userId) {
        Boolean isUpdated = userService.updateEmployee(userId, employeeDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(EmployeeConstants.STATUS_200, EmployeeConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(EmployeeConstants.STATUS_417, EmployeeConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete-employee/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteEmployee(@PathVariable Long userId) {
        Boolean isDeleted = userService.deleteEmployee(userId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(EmployeeConstants.STATUS_200, EmployeeConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(EmployeeConstants.STATUS_417, EmployeeConstants.MESSAGE_417_DELETE));
        }
    }


    @GetMapping("/get-employee/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> viewEmployee(@PathVariable Long userId) {
        UserDto employeeDTO = userService.getEmployeeById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(employeeDTO);
    }

    @GetMapping("/get-department-employees/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<EmployeesDTO>> viewEmployeesByDepartment(@PathVariable Long departmentId) {
        List<EmployeesDTO> employeesByDepartment = departmentService.getEmployeesByDepartment(departmentId);

        return ResponseEntity.status(HttpStatus.OK).body(employeesByDepartment);
    }
//
//    private UserDto convertToDto(Employees user) {
//        return new UserDto(
//                user.getFirstName(),
//                user.getLastName(),
//                user.getEmail(),
//                user.getPhoneNumber()
//        );
//    }


}