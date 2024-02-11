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

    public PetAdopterSignupRequest(String email, String password, String role, String firstName, String lastName,
                                   String phone_number, String address) {
        super(email, password, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone_number = phone_number;
    }

}
