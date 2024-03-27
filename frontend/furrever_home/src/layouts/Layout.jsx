import React, { useState,useEffect } from 'react'
import Footer from '../components/Footer'
import Header from '../components/Header'
import { readLocalStorage, saveLocalStorage } from '../utils/helper';
import axios from 'axios';
import { Spinner } from '@material-tailwind/react';

const Layout = ({ children }) => {

    const userid = readLocalStorage("id")
    const token = readLocalStorage("token")
    const [user,setUser] = useState({})
    const [loading, setLoading] = useState(false)
    // const User = JSON.parse(readLocalStorage("User"))
    
    const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`

    const getShelter= ()=>{
        axios.get(`${baseurl}/shelter/single/${userid}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            }
          })
            .then(response => {
              console.log(response.data)
              saveLocalStorage("User", JSON.stringify(response.data));
              setLoading(true)
            })
            .catch(error => {
              console.log(error);
            })
    }

    const getAdopter= ()=>{
        axios.get(`${baseurl}/petadopter/${userid}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            }
          })
            .then(response => {
              console.log(response.data)
              saveLocalStorage("User", JSON.stringify(response.data));
              setLoading(true)
            })
            .catch(error => {
              console.log(error);
            })
    }
    useEffect(() => {

        if(readLocalStorage("role")==="SHELTER"){
            getShelter()
        }else{
            getAdopter()
        }
    }, [])
    return (
        loading?
        <>
        
            <Header />
            {children}
            <Footer />

        </>
        :
        <Spinner color="green" className="flex justify-center align-middle" />
    )
}

export default Layout