import React, { useState } from 'react'
import axios from 'axios'
import Logo from '../components/Logo'

const ForgotPassword = () => {

  return (
    <>
      <div className="flex min-h-full flex-1 flex-col m-8 justify-center px-6 py-12 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <Logo />
          <h2 className="mt-10 font-primary text-center text-2xl font-bold leading-9 tracking-tight text-teal">
            Enter your registered email to reset your password
          </h2>
        </div>

        <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
          <form className="space-y-6" action="#" method="POST" onSubmit={handleSubmit}>

          <div>
            
            <label htmlFor="email" className="text-sm font-medium leading-6 text-gray-900 flex">
               Email address
            </label>
            <div className="mt-2">
              <input
                id="email"
                name="email"
                type="email"
                autoComplete="email"
                required
                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                placeholder='Your Email Address'
                onChange={handleEmailChange}
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

export default ForgotPassword