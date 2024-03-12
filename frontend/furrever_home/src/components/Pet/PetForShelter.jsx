import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import PetDetail from './PetDetail';
import ShelterDetail from './ShelterDetail';
import { readLocalStorage } from '../../utils/helper';
import { ToastContainer, toast } from "react-toastify";

const PetForShelter = () => {
    const location = useLocation();
    const petId = location.state.id;
    const [pet,setPet] = useState({
      type:"",
      breed:"",
      birthDate:"",
      gender:"",
      color:""
    })
    const [shelter,setShelter] = useState({
        name:"Furrever_home",
        address:"Brunswick Street",
        city:"Halifax",
        country:"Canada",
        contact:"1213123",
        image:""
      });
    const token = readLocalStorage("token");
    const shelterId = readLocalStorage("shelterID")

    console.log(petId);

  return (

    <div className="w-full py-6 space-y-6 bg-gray-100">
      <div className="container mx-auto py-8">
        <div className="grid gap-6 grid-cols-2">
          <PetDetail pet={pet} petId={petId} sid={shelterId} />
          <ShelterDetail shelter={shelter} petId={petId}/>
        </div>
      </div>
    </div>
  )
}

export default PetForShelter
