package com.furreverhome.Furrever_Home.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class AdopterPetRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petID", referencedColumnName = "petID", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adopterID", referencedColumnName = "id", nullable = false)
    private PetAdopter petAdopter;
}