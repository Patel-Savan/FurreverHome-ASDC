package com.furreverhome.Furrever_Home.dto.profile;

public record UpdateShelterProfileRequestDto(
        String name,
        String location,
        Long capacity,
        String contact,
        String imageBase64,
        String license
) {
}
