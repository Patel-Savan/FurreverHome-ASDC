import { useState , React, useEffect} from 'react'
import { useLocation } from 'react-router-dom'
import { readLocalStorage } from '../../utils/helper'
import PetDetail from './PetDetail'
import ShelterDetail from './ShelterDetail'
import axios from 'axios'
import { ToastContainer, toast } from "react-toastify";

const PetForAdopter = () => {
    
    const location = useLocation();
    const petId = location.state.id;
    const [pet,setPet] = useState({
      type:"Dog",
      breed:"German",
      birthDate:"30-12-2000",
      gender:"Male",
      color:"Black",
      image:""
    })
    const [shelter,setShelter] = useState({
      name:"Furrever_home",
      address:"Brunswick Street",
      city:"Halifax",
      country:"Canada",
      contact:"1213123",
    });
    const token = readLocalStorage("token");
    let shelterId = "1";

    console.log(petId);

  return (

    <div className="w-full py-6 space-y-6 bg-gray-100">
      <div className="container mx-auto py-8">
        <div className="grid gap-6 grid-cols-3 mx-auto" >
            <PetDetail pet={pet} petId={petId} sid={shelterId}/>
            <ShelterDetail shelter={shelter} petId={petId}/>     
        </div>
      </div>
    </div>
  )
}

export default PetForAdopter
