package com.furreverhome.Furrever_Home.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="shelter")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    private Long capacity;

    private String contact;

    @Column(columnDefinition = "longblob")
    private byte[] image;

    @Column(columnDefinition = "longblob")
    private byte[] license;

//    @CreationTimestamp
//    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
