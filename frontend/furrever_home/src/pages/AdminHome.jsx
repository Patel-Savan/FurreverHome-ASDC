import React, { useState,useEffect } from 'react'


import ShelterTable from '../components/Admin/ShelterTable';
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../utils/helper'

import pets from '../dummydata/pets'
import axios from 'axios';
import { toast } from "react-toastify";

const AdminHome = ({children}) => {



  return (
    <section>

      <div className='lg:flex '>




        <div className=' sm:w-full'>

          <ShelterTable/>

        </div>

      </div>
    </section>
  )
}

export default AdminHome
