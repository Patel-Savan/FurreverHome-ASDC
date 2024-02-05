package com.furreverhome.Furrever_Home.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String v_id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone_number;

    private String address;
}
