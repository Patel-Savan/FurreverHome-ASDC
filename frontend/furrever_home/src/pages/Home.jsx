import React from 'react'
import Adoption from '../components/Adoption'
import Hero from '../components/Hero'
import Newsletter from '../components/Newsletter'
import Pets from '../components/Pets'
import Services from '../components/Services'

const Home = () => {
    return (
        <>
            <div className='mx-auto overflow-hidden'>
                <Hero />
                <Pets />
                <Services />
                <Adoption />
                <Newsletter />
            </div>
        </>
    )
}

export default Home