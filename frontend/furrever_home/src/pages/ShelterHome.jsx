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
  const id = readLocalStorage("id");
  console.log(id)

  // useEffect(()=>{

  //   // axios.get(`${baseurl}/${id}`,{
  //   //   headers: {
  //   //     Authorization: `Bearer ${token}`,
  //   //   }
  //   // })
  //   //   .then(response => {
  //   //     console.log(response.data)
  //   //     saveLocalStorage("User",JSON.stringify(response.data));
  //   //   })
  //   //   .catch(error => {
  //   //     console.log(error);
  //   //   })

  // },[])



  return (
    <section>

      <div className='lg:flex '>

        {/* <div className='lg:w-[20%]'>
          <Sidebar />
        </div> */}



        <div className=' sm:w-full'>

          <PetsTable />

        </div>

      </div>
    </section>
  )
}

export default ShelterHome
