import React, { useState,useEffect } from 'react'

import Sidebar from '../components/Shelter/Sidebar'
import PetsTable from '../components/Shelter/PetsTable';
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../utils/helper'

import pets from '../dummydata/pets'
import axios from 'axios';
import { toast } from "react-toastify";

const ShelterHome = ({children}) => {

  const [search, setSearch] = useState('');

  const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`;
  const token = readLocalStorage("token")
  const [pets, setPets] = useState([])
  const sid = readLocalStorage("shelterID");
  const id = readLocalStorage("id");
  console.log(id)

  const getPet =()=>{
    axios.get(`${baseurl}/shelter/${sid}/pets`, {
        headers: {
            Authorization: `Bearer ${token}`,
        }
    })
        .then(response => {
            setPets(response.data)
            console.log(response.data)
            setLoading(true)
            console.log(pets)

        })
        .catch(error => {
            console.log(error);
        })
}

  useEffect(()=>{

    axios.get(`${baseurl}/shelters/${id}`,{
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        console.log(response.data)
        saveLocalStorage("User",JSON.stringify(response.data));
      })
      .catch(error => {
        console.log(error);
      })

      getPet()

  },[pets.length])



  return (
    <section>

      <div className='lg:flex '>

        {/* <div className='lg:w-[20%]'>
          <Sidebar />
        </div> */}



        <div className=' sm:w-full'>

          <PetsTable pets={pets} />

        </div>

      </div>
    </section>
  )
}

export default ShelterHome
