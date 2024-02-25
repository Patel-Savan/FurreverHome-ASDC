import React from 'react'
import Hero from '../components/Hero'
import Pets from '../components/Pets'
import Services from '../components/Services'
import Adoption from '../components/Adoption'
import Newsletter from '../components/Newsletter'
import Footer from '../components/Footer'

const Home = () => {
    return (
        <>
            <div className='mx-auto overflow-hidden'>
                <Hero/>
                <Pets/>
                <Services/>
                <Adoption/>
                <Newsletter/>
            </div>    
        </>
    )
}

export default Home