package com.codesofscar.authentication.controller;

import com.codesofscar.authentication.dto.LoginDto;
import com.codesofscar.authentication.dto.SignUpDto;
import com.codesofscar.authentication.dto.UserDto;
import com.codesofscar.authentication.repository.AuthRepository;
import com.codesofscar.authentication.service.impl.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private  AuthRepository authRepository;
    private AuthServiceImpl userService;
//    private LogoutServiceImpl logoutService;

    @Autowired
    public AuthController(AuthServiceImpl userService, AuthRepository authRepository) {
        this.userService = userService;
//        this.logoutService = logoutService;
        this.authRepository = authRepository;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUpEmployee(@RequestBody SignUpDto signupDto, final HttpServletRequest request){
        userService.saveEmployee(signupDto);
        return new ResponseEntity<>("Signup successful", HttpStatus.OK);
    }

    @PostMapping("/sign-up/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> signUpAdmin(@RequestBody SignUpDto adminDto, final HttpServletRequest request){
        userService.saveAdmin(adminDto);
        return new ResponseEntity<>("Admin signup successful", HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String result = userService.logInEmployee(loginDto);
        return new ResponseEntity<>("Login successful!", HttpStatus.OK);
    }

    @PostMapping("/generate-new-user")
    public ResponseEntity<String> getNewUserForEmployee(@RequestBody SignUpDto signupDto, final HttpServletRequest request){
        userService.saveEmployee(signupDto);
        return new ResponseEntity<>("New Employee Addition successful", HttpStatus.OK);
    }


    @GetMapping("/generate-user")
    public ResponseEntity<UserDto> getUserForEmployee(Long userId){
        UserDto userDto  = userService.getUserForEmployee(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/update-user/{userId}")
    public ResponseEntity<String> updateUserForEmployee(@PathVariable Long userId, @RequestBody UserDto userDto){
        userService.updateUserForEmployee(userId, userDto);
        return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
    }

    @PostMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUserForEmployee(@PathVariable Long userId){
        userService.deleteUserForEmployee(userId);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }




//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
//        logoutService.logout(request, response, authentication);
//        return ResponseEntity.ok("Logout successful");
//    }

}
