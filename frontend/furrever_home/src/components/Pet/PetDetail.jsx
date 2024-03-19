import React, { useEffect, useState } from 'react'
import Pet1 from '/img/pets/1.png'
import { readLocalStorage } from '../../utils/helper'
import { toast } from 'react-toastify'
import UpdatePetDetails from '../Shelter/UpdatePetDetails'
import axios from 'axios'



const PetDetail = ({
  pet,
  petId,
  shelterId
}) => {

  const role = readLocalStorage("role")
  const userId = readLocalStorage("id")
  const token = readLocalStorage("token")
  const [reqExist,setReqExist] = useState(false)
  console.log("Hello",pet)

  const handleAdoptionRequest = () => {
    setReqExist(true)
  }

  return (
        <div className="bg-white w- relative group overflow-hidden rounded-lg shadow-lg hover:shadow-xl transition-transform duration-300 ease-in-out hover:-translate-y-2">
            <img
              alt="Rusty"
              className="w-[50%] cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 h-auto mx-auto"
              src={pet.petImage}
            />
            <div className="p-4 dark:bg-gray-950">

            <h2 className="text-2xl font-bold">Type : {pet.type}</h2>
            <div className="grid gap-2 text-lg font-normal py-2">
              <p className="flex items-center gap-1">Birth date : {pet.birthDate}</p>
              <p className="flex items-center gap-1">Gender : {pet.gender}</p>
              <p className="flex items-center gap-1">Breed : {pet.breed}</p>
              <p className="flex items-center gap-1">Color : {pet.color}</p>          
            </div>
            <div>
            { role == "PETADOPTER" &&
                reqExist ? <p className="flex items-center fonr-bold text-green-500 gap-1">You have sent request for this pet </p> : <button type="button" onClick={handleAdoptionRequest} className="btn btn-orange mx-auto lg:h-10 sm:h-15">Adopt</button>/* : <UpdatePetDetails petId={petId} sid={shelterId} />*/
            }
            </div>
              
            </div>
        </div>
  )
}


export default PetDetail
