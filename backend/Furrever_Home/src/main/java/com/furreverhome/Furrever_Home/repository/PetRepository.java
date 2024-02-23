package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
