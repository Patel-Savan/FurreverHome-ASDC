import React, { useState,useEffect } from 'react'


import ShelterTable from '../components/Admin/ShelterTable';
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../utils/helper'

import pets from '../dummydata/pets'
import axios from 'axios';
import { toast } from "react-toastify";

const AdminHome = ({children}) => {

    const [shelters,setShelters] = useState({})
  const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter/shelters`;
  const token = readLocalStorage("token")
  const id = readLocalStorage("id");
  console.log(id)

  useEffect(()=>{

    axios.get(`${baseurl}`,{
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        setShelters(response.data)
        saveLocalStorage("User",JSON.stringify(response.data));
      })
      .catch(error => {
        console.log(error);
      })

  },[])



  return (
    <section>

      <div className='lg:flex '>




        <div className=' sm:w-full'>

          <ShelterTable shelters={shelters}/>

        </div>

      </div>
    </section>
  )
}

export default AdminHome
