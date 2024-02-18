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

    private String imageBase64;

    private String license;

//    private LocalDateTime createdAt;

    private String message;

}
