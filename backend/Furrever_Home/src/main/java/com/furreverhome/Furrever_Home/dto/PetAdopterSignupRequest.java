package com.furreverhome.Furrever_Home.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("petadopter")
public class PetAdopterSignupRequest extends SignupRequest{

    private String firstName;

    private String lastName;

    private String phone_number;

    private String address;

}
