import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Home from '../pages/Home'
import Layout from '../layouts/Layout'
import PetAdopterRegister from '../components/Register/PetAdopterRegister'
import ShelterRegister from '../components/Register/ShelterRegister'


const Router = () => {
    return (
        /* All the Routes for Frontend */
        <Routes>
            {/* 
            @Route 
            @Component Returns Home Component
            
            Route for Home Component
            */}
            <Route path="/" element={<Layout><Home /></Layout>} />

            {/* 
            @Route
            @Component Returns PetAdopterRegister Component

            Route for Pet Adopter Registration
            */}
            <Route path="/register/adopter" element={<PetAdopterRegister />} />

            {/* 
            @Route
            @Component Returns ShelterRegister Component

            Route for Shelter Registration
            */}
            <Route path="/register/shelter" element={<ShelterRegister />} />

        </Routes>
    )
}

export default Router