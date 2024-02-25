package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.services.adminservices.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/shelter/{email}/{status}")
    public ResponseEntity<?> changeVerifiedStatus(@PathVariable String email, @PathVariable String status) {
        System.out.println(email);
        System.out.println(status);
        boolean success = adminService.changeVerifiedStatus(email, status);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
