package com.furreverhome.Furrever_Home.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petID;
    private String type;
    private String breed;
    private String colour;
    private String gender;
    private Date birthdate;

    @Lob
    @Column(length = 100000)
    private String petImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelterID", referencedColumnName = "id")
    private Shelter shelter;
}
