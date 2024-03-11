package com.furreverhome.Furrever_Home.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "lostpet")
public class LostPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String breed;
    private String colour;
    private String gender;

    @Lob
    @Column(length = 10000000)
    private String petImage;

    private String phone;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
