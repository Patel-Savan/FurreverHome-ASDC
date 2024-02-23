package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.entities.PetAdopter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetAdopterRepository extends JpaRepository<PetAdopter, Long> {
    Optional<PetAdopter> findByUserId(Long userId);
}
