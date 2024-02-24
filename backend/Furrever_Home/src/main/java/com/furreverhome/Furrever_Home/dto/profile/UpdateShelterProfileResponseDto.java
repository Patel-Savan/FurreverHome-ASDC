package com.furreverhome.Furrever_Home.dto.profile;

public record UpdateShelterProfileResponseDto(
        Long id,
        String name,
        String location,
        Long capacity,
        String contact,
        String imageBase64,
        String license
) {
}
