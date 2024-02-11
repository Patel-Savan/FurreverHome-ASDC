package com.furreverhome.Furrever_Home.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonTypeName("shelter")
public class ShelterSignupRequest extends SignupRequest{
    private String name;

    private String location;

    private Long capacity;

    private String contact;

    private byte[] image;

    private byte[] license;

//    private LocalDateTime createdAt;

    private String message;

    public ShelterSignupRequest(String email, String password, String role, String name, String location, Long capacity,
                                String contact, byte[] image, byte[] license, String message) {
        super(email, password, role);
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.contact = contact;
        this.image = image;
        this.license = license;
//        this.createdAt = createdAt;
        this.message = message;
    }
}
