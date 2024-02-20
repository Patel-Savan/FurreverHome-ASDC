import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Home from '../pages/Home'
import Layout from '../layouts/Layout'
import PetAdopterRegister from '../components/Register/PetAdopterRegister'
import ShelterRegister from '../components/Register/ShelterRegister'
import Login from '../pages/login'
import ForgotPassword from '../pages/ForgotPassword'
import ResetPassword from '../pages/ResetPassword'
import PetAdopterHome from '../pages/PetAdopterHome'
import ShelterHome from '../pages/ShelterHome'

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

            {/* 
            @Route
            @Component Returns Login Component

            Route for Login
            */}
            <Route path="/login" element={<Login />} />

            {/* 
            @Route
            @Component Returns ResetPassword Component

            Route for ResetPassword
            */}
            <Route path="/reset-password" element={<ResetPassword />} />

            {/* 
            @Route
            @Component Returns ForgotPassword Component

            Route for ForgotPassword
            */}
            <Route path="/forgot-password" element={<ForgotPassword />} />

             {/* 
            @Route
            @Component Returns PetAdopterHome Component

            Route for PetAdopter Home
            */}
            <Route path="/PetAdopterHome" element={<PetAdopterHome />} />

             {/* 
            @Route
            @Component Returns ShelterHome Component

            Route for Shelter Home
            */}
            <Route path='/ShelterHome' element={<ShelterHome />} />

        </Routes>
    )
}

export default Router