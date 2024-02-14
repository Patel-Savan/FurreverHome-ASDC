import React, { useState } from 'react'
import { useLocation, useNavigate} from 'react-router-dom'
import axios from 'axios'
import Logo from '../components/Logo'

const ResetPassword = () => {

    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const token = queryParams.get("token");
    const navigate = useNavigate();

    const [newPassword,setNewPassword] = useState('');
    const [verifyNewPassword,setVerifyNewPassword] = useState('');

    const handleNewPasswordChange=(event) =>{
        setNewPassword(event.target.value);
    }

    const handleVerifyNewPasswordChange=(event) =>{
        setVerifyNewPassword(event.target.value);
    }

    const handleSubmit = (event) =>{
        event.preventDefault();

        if(newPassword == verifyNewPassword ){
            axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/auth/resetPassword`,{newPassword,verifyNewPassword,token})
                .then(response =>{
                  alert("Password Successfully reset.");
                  navigate('/login');
                })
                .catch(error =>{
                    alert(error.message);
                })
        }
        else{
          alert("Password and Confirm Password doesn't macth");
        }
    }

  return (
    <>
      <div className="flex min-h-full flex-1 flex-col m-8 justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <Logo />
          <h2 className="mt-10 font-primary text-center text-2xl font-bold leading-9 tracking-tight text-teal">
            Reset your Password
          </h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6" action="#" method="POST" onSubmit={handleSubmit}>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  New Password
                </label>
              </div>
              <div className="mt-2">
                <input
                  id="newPassword"
                  name="newPassword"
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Enter Your Password'
                  onChange={handleNewPasswordChange}
                />
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                   Repeat Password
                </label>
              </div>
              <div className="mt-2">
                <input
                  id="verifyNewPassword"
                  name="verifyNewPassword"
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Confirm New Password'
                  onChange={handleVerifyNewPasswordChange}
                />
              </div>
            </div>

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-orange px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                Reset
              </button>
            </div>
          </form>
         
        </div>
      </div>
    </>
  )
}

export default ResetPassword