package com.furreverhome.Furrever_Home.dto.Pet;


import com.furreverhome.Furrever_Home.entities.PetAdopter;
import lombok.Data;

import java.util.List;

@Data
public class PetAdoptionRequestResponseDto {
    private Long petID;
    private List<PetAdopter> petAdopters;
}
