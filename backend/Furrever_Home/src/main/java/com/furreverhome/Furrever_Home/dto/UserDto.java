package com.furreverhome.Furrever_Home.dto;

import com.furreverhome.Furrever_Home.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    private String name;

    private String email;

    private Role userRole;
}
