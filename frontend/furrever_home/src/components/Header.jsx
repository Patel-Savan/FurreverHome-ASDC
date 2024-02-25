import React from 'react';

import ImgLogo from '/img/logo/LogoWithText_NoBG.png'
import { Link } from 'react-router-dom';
import verifyAuthentication from '../hooks/verifyAuthentication';
import { deleteLocalStorage, readLocalStorage } from '../utils/helper';
import { useNavigate } from 'react-router-dom';
import Modal from './Modal/Modal';
import {
  Avatar, Menu,
  MenuHandler,
  MenuList,
  MenuItem,
  Button,
} from "@material-tailwind/react";

const Header = () => {
  const userToken = verifyAuthentication()
  const user = JSON.parse(readLocalStorage("User"))
  console.log(userToken)
  const navigate = useNavigate();

  const handleLogout = () => {
    deleteLocalStorage("token");
    deleteLocalStorage("User");
    deleteLocalStorage("role");
    deleteLocalStorage("id");
    navigate("/login");
  }
  // backdrop-blur-sm

  return (

    <header className='lg:w-full lg:left-0 top-0 shadow-md bg-cream/75 backdrop-blur-sm  border-b-[1px]  p-1 sticky z-10'>
      <div className='container mx-auto flex flex-col gap-y-6 lg:flex-row h-full justify-between items-center relative'>



        {/* Todo: Add Link Component*/}
        
        

        {
          (userToken.userRole === 'SHELTER')
            ?
            (
              //   <nav className='text-xl flex gap-x-4 lg:gap-x-12'>
              //   <Link to='/shelter/home' >
              //     Pets
              //   </Link>
              //   {/* <Link to='/'>
              //     Pets
              //   </Link> */}
              //   {/* <Link to='/shelter/register-pet'>
              //     Register Pet
              //   </Link> */}
              //   <Link to='/'>
              //     Manage
              //   </Link>
              // </nav>
              (
                <Link to='/shelter/home'>
                  <img className=' h-12' src={ImgLogo} />
                </Link>
              )
            )
            :
            (
              (userToken.userRole === 'PETADOPTER')
                ?
                (
                  <>
                    <Link to='/adopter/home'>
                      <img className=' h-12' src={ImgLogo} />
                    </Link>
                    <nav className='text-xl flex gap-x-4 lg:gap-x-12'>
                      <Link to='/adopter/home' >
                        Shelters
                      </Link>
                      {/* <Link to='/'>
                        Pets
                      </Link> */}
                      <Link to='/'>
                        Adopt
                      </Link>
                      <Link to='/'>
                        Lost&Found
                      </Link>
                    </nav>
                  </>
                )

                :
                (
                  <Link to='/'>
                    <img className=' h-12' src={ImgLogo} />
                  </Link>
                )

            )
        }

        {
          userToken ?
            (
              <div className='flex gap-2'>




                <Menu>
                  <MenuHandler>
                    <button><Avatar src="https://docs.material-tailwind.com/img/face-2.jpg" alt="avatar" size="md" /></button>
                  </MenuHandler>
                  <MenuList>
                    
                    {
                      userToken.userRole==="SHELTER" 
                      ?
                      (<>
                        <MenuItem>
                    <Link to="/shelter/profile">
                    Profile
                    </Link>   
                    </MenuItem>
                      <MenuItem>
                        <Link to="/shelter/home">
                        Dashboard
                        </Link>   
                        </MenuItem> 
                        </>  )
                      :
                      (
                      <><MenuItem>
                      <Link to="/adopter/profile">
                      Profile
                      </Link>   
                      </MenuItem>
                      <MenuItem>
                        <Link to="/adopter/home">
                        Dashboard
                        </Link>   
                        </MenuItem> </>  )
                    }  
                               
                    <button type="submit"
                      className="btn btn-outline align-middle justify-center"
                      onClick={handleLogout}
                    >
                      Sign Out
                    </button>
                  </MenuList>
                </Menu>
              </div>

            ) :
            (
              <div className='flex gap-4 p-2 pl-5'>
                <Link to='/login'>
                  <button className='btn btn-outline'>Login</button>
                </Link>

                <Modal />


              </div>)
        }



      </div>

    </header >

  )
};

export default Header;
