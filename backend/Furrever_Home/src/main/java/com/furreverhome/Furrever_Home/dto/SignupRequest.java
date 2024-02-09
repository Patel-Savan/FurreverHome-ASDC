package com.furreverhome.Furrever_Home.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;

    private String password;

    private String role;
}
