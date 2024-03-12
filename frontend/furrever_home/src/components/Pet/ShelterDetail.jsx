import React from 'react'
import Vaccination from './Vaccination';

const ShelterDetail = ({
  shelter,
  petId
}) => {

  const role = "petadopter";
  return (
      <div className="bg-white relative group overflow-hidden rounded-lg shadow-lg hover:shadow-xl transition-transform duration-300 ease-in-out hover:-translate-y-2">
        
        <div className="p-4 dark:bg-gray-950">
          <h1 className="text-1xl font-bold">Shelter Details</h1>
          <h2 className="text-lg font-normal">{shelter.name}</h2>
          <div className="grid gap-2 text-sm py-2">
            <p className="flex items-center gap-1">{shelter.address} </p>
            <p className="flex items-center gap-1">{shelter.city} , {shelter.country}</p>
            <p className="flex items-center gap-1">{shelter.contact}</p>          
          </div>
        </div>
        <Vaccination petId={petId} />
      </div>
  )
}

export default ShelterDetail
