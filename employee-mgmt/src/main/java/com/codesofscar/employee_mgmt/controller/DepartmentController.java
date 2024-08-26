package com.codesofscar.employee_mgmt.controller;

import com.codesofscar.employee_mgmt.constants.DepartmentConstants;
import com.codesofscar.employee_mgmt.dto.DepartmentDTO;
import com.codesofscar.employee_mgmt.dto.ResponseDTO;
import com.codesofscar.employee_mgmt.entity.Department;
import com.codesofscar.employee_mgmt.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {


    private final DepartmentServiceImpl departmentService;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/get-details/{departmentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<DepartmentDTO> viewDepartment(@PathVariable Long departmentId) {
        DepartmentDTO departmentDTO = departmentService.getDepartmentDetails(departmentId);

        return ResponseEntity.status(HttpStatus.OK).body(departmentDTO);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO departmentDTO) {
        Department savedDepartment = departmentService.saveDepartment(departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentDTO);
    }

    @DeleteMapping("/update/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> updateDepartment(@RequestBody DepartmentDTO departmentDTO,
                                                        @PathVariable Long departmentId) {
        Boolean isUpdated = departmentService.updateDepartment(departmentId, departmentDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(DepartmentConstants.STATUS_200, DepartmentConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(DepartmentConstants.STATUS_417, DepartmentConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteDepartment(@PathVariable Long userId) {
        Boolean isDeleted = departmentService.deleteDepartment(userId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(DepartmentConstants.STATUS_200, DepartmentConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(DepartmentConstants.STATUS_417, DepartmentConstants.MESSAGE_417_UPDATE));
        }
    }




}
