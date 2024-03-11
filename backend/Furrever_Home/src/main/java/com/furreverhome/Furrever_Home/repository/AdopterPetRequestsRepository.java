package com.furreverhome.Furrever_Home.repository;

import com.furreverhome.Furrever_Home.entities.AdopterPetRequests;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdopterPetRequestsRepository extends JpaRepository<AdopterPetRequests, Long> {
    List<PetAdopter> findByPet(Pet pet);
}
