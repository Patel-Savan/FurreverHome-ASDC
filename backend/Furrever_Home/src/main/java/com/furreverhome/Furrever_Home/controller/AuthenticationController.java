package com.furreverhome.Furrever_Home.controller;


import com.furreverhome.Furrever_Home.dto.*;
import com.furreverhome.Furrever_Home.services.AuthenticationService;
import com.furreverhome.Furrever_Home.services.PetAdopterAuthenticationService;
import com.furreverhome.Furrever_Home.services.ShelterAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final PetAdopterAuthenticationService petAdopterAuthenticationService;

    private final ShelterAuthenticationService shelterAuthenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        if (signupRequest instanceof PetAdopterSignupRequest) {
            return ResponseEntity.ok(petAdopterAuthenticationService.signup((PetAdopterSignupRequest) signupRequest));
        } else if (signupRequest instanceof ShelterSignupRequest) {
            return ResponseEntity.ok(shelterAuthenticationService.signup((ShelterSignupRequest) signupRequest));
        }
        return ResponseEntity.ok(null);
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
