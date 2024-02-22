import React from 'react';

import Logo from '/img/header/logo.svg'
import TextLogo from '/img/logo/LogoOnlyText.png'
import ImgLogo from '/img/logo/LogoWithText_NoBG.png'
import { Link } from 'react-router-dom';

const Header = () => {
  return(

    <header className='py-4 lg:absolute lg:w-full lg:left-0'>
      <div className='container mx-auto flex flex-col gap-y-6 lg:flex-row h-full justify-between items-center relative'>
         
         {/* Todo: Add Link Component*/}
         <Link to='/'>
        <img className=' h-16' src={ImgLogo} />
        </Link>
        <nav className='text-xl flex gap-x-4 lg:gap-x-12'>
          <Link to='/' >
            Shelters
          </Link>
          <Link to='/'>
            Pets
          </Link>
          <Link to='/'>
            Adopt
          </Link>
          <Link to='/'>
            Lost&Found
          </Link>
        </nav>

        <Link to='/login'>
        <button className='btn btn-primary lg:btn-outline' > Login / Register</button>
        </Link>

      </div>

    </header>

  )
};

export default Header;
