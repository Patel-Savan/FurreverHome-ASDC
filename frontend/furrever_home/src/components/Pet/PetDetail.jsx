import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { toast } from 'react-toastify'
import { readLocalStorage } from '../../utils/helper'
import UpdatePetDetails from '../Shelter/UpdatePetDetails'



const PetDetail = ({
  pet,
  petId
}) => {

  const role = readLocalStorage("role")
  const userId = readLocalStorage("petadopterID")
  const token = readLocalStorage("token")
  const [reqExist, setReqExist] = useState(false)
  const sid = readLocalStorage("shelterID")
  console.log(role)

  if (role === "PETADOPTER") {
    useEffect(() => {

      axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter/pet/adopt/requestexists`, {
        params: {
          petID: petId,
          petAdopterID: userId
        },
        headers: {
          Authorization: `Bearer ${token}`
        }
      })
        .then(response => {
          setReqExist(true)
        })
        .catch(error => {
          console.log(error)
        })
    }, [])
  }

  const handleAdoptionRequest = () => {
    axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter/pet/adopt`, {
      petID: petId,
      petAdopterID: userId
    }, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        setReqExist(true)
        toast.success("Adoption request sent")
      })
      .catch(error => {
        console.log(petId + " " + petAdopterID)
        console.log(error)
        toast.error("Cannot send request, Please Try again later")
      })
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
          <p className="flex items-center gap-1">Birth date : {pet.birthdate}</p>
          <p className="flex items-center gap-1">Gender : {pet.gender}</p>
          <p className="flex items-center gap-1">Breed : {pet.breed}</p>
          <p className="flex items-center gap-1">Color : {pet.colour}</p>
        </div>
        <div>

          {role === "PETADOPTER" ?
            reqExist ? <p className="flex items-center fonr-bold text-green-500 gap-1">You have sent request for this pet </p> : <button type="button" onClick={handleAdoptionRequest} className="btn btn-orange mx-auto lg:h-10 sm:h-15">Adopt</button>
            :
            <div className="flex justify-center">
              <span className="text-lg font-normal py-2 mr-3">Want to edit Pet details ? </span>
              <UpdatePetDetails pets={pet} sid={sid} />
            </div>
          }

        </div>

      </div>
    </div>
  )
}


export default PetDetail
