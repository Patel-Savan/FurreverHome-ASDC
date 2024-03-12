import React, { useEffect, useState } from 'react'
import { readLocalStorage } from '../../utils/helper'
import { toast } from 'react-toastify'
import axios from 'axios'

const Vaccination = (petId) => {

  const [vac,setVac] = useState()
  const baseurl= `${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter`;
  const token = readLocalStorage("token")

  return (
    <div className="p-4 dark:bg-gray-950">
        <h1 className="text-1xl font-bold">Vaccination Details</h1>
        <div className="grid gap-2 text-lg font-normal py-2">
          <p className="flex items-center gap-1">Age</p>
          <p className="flex items-center gap-1">Gender</p>
          <p className="flex items-center gap-1">Breed</p>
          <p className="flex items-center gap-1">Color</p>          
        </div>
    </div>
  )
}

export default Vaccination
