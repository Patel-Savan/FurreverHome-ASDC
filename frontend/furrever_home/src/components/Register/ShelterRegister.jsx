import React, { useState } from 'react'
import Logo from '../Logo'
import { Link } from 'react-router-dom';
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import axios from 'axios';

const ShelterRegister = () => {

  return (
    <>
      <div className="flex min-h-full flex-1 flex-col m-8 justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
          <Logo />
          <h2 className="mt-10 font-primary text-center text-2xl font-bold leading-9 tracking-tight text-teal">
            Create Your New Account
          </h2>
        </div>

        <div className="mt-5 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6" method="POST" onSubmit={handleSubmit} encType="multipart/form-data">

            <div>

              <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                Shelter Name
              </label>
              <div className="mt-1">
                <input
                  id="name"
                  name="name"
                  type="text"
                  value={formData.name}
                  onChange={handleChange}
                  autoComplete="text"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Enter Shelter Name'
                />
              </div>
            </div>

            <div>
              <label htmlFor="email" className="text-sm font-medium leading-6 text-gray-900 flex">
                Email address
              </label>
              <div className="mt-1">
                <input
                  id="email"
                  name="email"
                  type="email"
                  value={formData.email}
                  onChange={handleChange}
                  autoComplete="email"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Shelter Email Address'
                />
              </div>
            </div>

            <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  Password
                </label>
              </div>
              <div className="mt-1">
                <input
                  id="password"
                  name="password"
                  type="password"
                  value={formData.password}
                  onChange={handleChange}
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Enter a Password'
                />
              </div>
            </div>

            <div>
              <label htmlFor="contact" className="text-sm font-medium leading-6 text-gray-900 flex">
                Contact Number
              </label>
              <div className="mt-1">
                <input
                  id="contact"
                  name="contact"
                  type="tel"
                  value={formData.contact}
                  onChange={handleChange}
                  autoComplete="tel"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Shelter Contact Number'
                />
              </div>
            </div>

            <div>
              <label htmlFor="contact" className="text-sm font-medium leading-6 text-gray-900 flex">
                Shelter Capacity
              </label>
              <div className="mt-1">
                <input
                  id="capacity"
                  name="capacity"
                  type="text"
                  value={formData.capacity}
                  autoComplete="text"
                  onChange={handleChange}
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Shelter Capacity'
                />
              </div>
            </div>

            <div className="">
              <label className="block text-sm font-medium leading-6 text-gray-900" >Upload Shelter Image</label>
              <input type="file"
                name='image'
                required
                onChange={(event) => { handleImage(event.target.files[0]) }}
                className="w-full text-black text-sm bg-white border file:cursor-pointer cursor-pointer file:border-0 file:py-2.5 file:px-4 file:bg-gray-100 file:hover:bg-gray-200 file:text-black rounded" />
              <p className="text-xs text-gray-400 mt-2">PNG, JPG are Allowed.</p>
            </div>

            <div className="">
              <label className="block text-sm font-medium leading-6 text-gray-900">Upload Valid Proof</label>
              <input type="file"
                name='license'
                required
                onChange={(event) => { handleLicense(event.target.files[0]) }}
                className="w-full text-black text-sm bg-white border file:cursor-pointer cursor-pointer file:border-0 file:py-2.5 file:px-4 file:bg-gray-100 file:hover:bg-gray-200 file:text-black rounded" />
              <p className="text-xs text-gray-400 mt-2">PNG, JPG are Allowed.</p>
            </div>

            {/* <div>
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                  Confirm Password
                </label>
              </div>
              <div className="mt-1">
                <input
                  id="password"
                  name="password"
                  type="password"
                  autoComplete="current-password"
                  required
                  className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                  placeholder='Confirm Your Password'
                />
              </div>
            </div> */}

            <div>
              <button
                type="submit"
                className="flex w-full justify-center rounded-md bg-orange px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                Register
              </button>
            </div>
          </form>

          <p className="mt-10 text-center text-sm text-gray-500">
            Already Registered?{' '}
            <Link to="/login" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
              Login Here
            </Link>
          </p>
          <p className="mt-2 text-center text-sm text-gray-500">
            Are you a Shelter Owner?{' '}
            <Link to="/login" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
              Shelter Login
            </Link>
          </p>
        </div>
      </div>
      {/* </div> */}
    </>
  )
}

export default ShelterRegister