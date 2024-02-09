package com.furreverhome.Furrever_Home.dto;

import lombok.Data;

@Data
public class PetAdopterSignupRequest extends SignupRequest{

    private String firstName;

    private String lastName;

    private String phone_number;

    private String address;
}
