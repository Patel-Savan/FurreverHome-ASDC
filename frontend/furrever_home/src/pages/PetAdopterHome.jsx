import React, { useEffect,useState } from 'react'
import { deleteLocalStorage, readLocalStorage } from '../utils/helper'
import { useNavigate} from 'react-router-dom'

const PetAdopterHome = () => {

  const navigate = useNavigate();

  const handleLogout=()=>{
    deleteLocalStorage("token");
    navigate("/login");
  }



  return (

    <div>
      Welcome to Pet Adopter Home Page

      <button type="submit"
              className="flex w-full justify-center rounded-md bg-orange px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              onClick={handleLogout}
      >
        Sign Out
      </button>
    </div> 
  )
}

export default PetAdopterHome
