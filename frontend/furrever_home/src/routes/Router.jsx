import React from 'react'
import { Routes, Route } from 'react-router-dom'
import Home from '../pages/Home'
import Layout from '../layouts/Layout'
import Login from '../pages/Login'
import ResetPassword from '../pages/ResetPassword'
import ForgotPassword from '../pages/ForgotPassword'
import PetAdopterRegister from '../components/Register/PetAdopterRegister'
import ShelterRegister from '../components/Register/ShelterRegister'
import PetAdopterHome from "../pages/PetAdopterHome"
import ShelterHome from "../pages/ShelterHome"

import PrivateRoutes from './PrivateRoutes'
import PrivateRoutesShelter from './PrivateRoutesShelter'
import PrivateRoutesAdopter from './PrivateRoutesAdopter'
import PrivateRoutesAdmin from './PrivateRoutesAdmin'
import PublicRoutes from './PublicRoutes'
import PageNotFound from '../components/PageNotFound'


const Router = () => {
    return (
        <Routes>
            <Route path="/" element={<Layout><Home /></Layout>} />

            <Route path="/login" element={<Login />} />


            <Route element={<PublicRoutes />}>
                <Route path="/register/adopter" element={<PetAdopterRegister />} />
                <Route path="/register/shelter" element={<ShelterRegister />} />
                <Route path="/reset-password" element={<ResetPassword />} />
                <Route path="/forgot-password" element={<ForgotPassword />} />
            </Route>

            <Route element={<PrivateRoutes />}>
                <Route element={<PrivateRoutesAdmin />}>
                    {/* <Route path="/admin/home" element={<Layout><AdminHome /></Layout>} /> */}
                </Route>
                <Route element={<PrivateRoutesAdopter />}>
                    <Route path="/adopter/home" element={<Layout><PetAdopterHome /></Layout>} /> 
                </Route>
                <Route element={<PrivateRoutesShelter />}>
                    <Route path="/shelter/home" element={<Layout><ShelterHome /></Layout>} />
                </Route>

            </Route>

            <Route path="*" element={<Layout><PageNotFound /></Layout>} />
        </Routes>
    )
}

export default Router