package com.furreverhome.Furrever_Home.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PetVaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long petVaccineID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petID", referencedColumnName = "petID", nullable = false)
    private Pet pet;

    private String vaccineName;

    private boolean isGiven;

    private Date date;

}
