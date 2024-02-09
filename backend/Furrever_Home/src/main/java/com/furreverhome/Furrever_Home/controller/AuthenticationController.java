package com.furreverhome.Furrever_Home.controller;


import com.furreverhome.Furrever_Home.dto.PetAdopterSignupRequest;
import com.furreverhome.Furrever_Home.dto.JwtAuthenticationResponse;
import com.furreverhome.Furrever_Home.dto.RefreshTokenRequest;
import com.furreverhome.Furrever_Home.dto.SigninRequest;
import com.furreverhome.Furrever_Home.entities.User;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody PetAdopterSignupRequest petAdopterSignupRequest) {
        return ResponseEntity.ok(authenticationService.signup(petAdopterSignupRequest));
    }

    @GetMapping("/verify/{email}")
    public Object verifyByEmail(@PathVariable String email) {
        if (authenticationService.verifyByEmail(email)) {
            return new RedirectView("https://www.google.com/");
        }
        return ResponseEntity.notFound();
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin (@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh (@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

}
