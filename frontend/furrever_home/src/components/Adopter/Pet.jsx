import React from 'react'
import { useParams } from 'react-router-dom';

const Pet = () => {

    const { id } = useParams();
    console.log(id);
    return (

        <div className="bg-cream py-8">
            <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex flex-col md:flex-row -mx-4">
                    <div className="md:flex-1 px-4">
                        <div className="h-[460px] rounded-lg bg-gray-300 dark:bg-gray-700 mb-4">
                            <img className="w-full h-full object-cover" src="https://images.dog.ceo/breeds/mountain-bernese/n02107683_6580.jpg" alt="Product Image" />
                        </div>
                        <div className="flex justify-center align-middle mb-4">
                            <div className="w-1/2 px-2">
                                <button className="w-full btn btn-orange ">Adopt</button>
                            </div>

                        </div>
                    </div>
                    <div className="md:flex-1 px-4">
                        <h2 className="text-2xl font-bold text-gray-800 dark:text-white mb-2">About</h2>
                        <p className="text-gray-600 dark:text-gray-300 text-sm mb-4">
                            Hi, I'm Checkers!

                            I love other doggies, and kids but I am unsure of those kitty cats at this time.

                            I am a timid soul, but I warm up quickly, and when I do - we will be best friends forever.

                            Would you like to be my friend? Apply to adopt me today!
                        </p>
                        <div className="flex mb-4">
                            {/* <div className="mr-4">
                            <span className="font-bold text-gray-700 dark:text-gray-300">Price:</span>
                            <span className="text-gray-600 dark:text-gray-300">$29.99</span>
                        </div> */}
                            <div>
                                <span className="font-bold text-gray-700 dark:text-gray-300">Not Adopted Yet</span>
                                {/* <span className="text-gray-600 dark:text-gray-300">In Stock</span> */}
                            </div>
                        </div>
                        <div className="mb-4">
                            {/* <span className="font-bold text-gray-700 dark:text-gray-300">Select Color:</span> */}
                            <div className="flex items-center mt-2">
                                <button className="w-6 h-6 rounded-full bg-gray-800 dark:bg-gray-200 mr-2"></button>
                                <button className="w-6 h-6 rounded-full bg-red-500 dark:bg-red-700 mr-2"></button>
                                <button className="w-6 h-6 rounded-full bg-blue-500 dark:bg-blue-700 mr-2"></button>
                                <button className="w-6 h-6 rounded-full bg-yellow-500 dark:bg-yellow-700 mr-2"></button>
                            </div>
                        </div>
                        {/* <div className="mb-4">
                            <span className="font-bold text-gray-700 dark:text-gray-300">Select Size:</span>
                            <div className="flex items-center mt-2">
                                <button className="bg-gray-300 dark:bg-gray-700 text-gray-700 dark:text-white py-2 px-4 rounded-full font-bold mr-2 hover:bg-gray-400 dark:hover:bg-gray-600">S</button>
                                <button className="bg-gray-300 dark:bg-gray-700 text-gray-700 dark:text-white py-2 px-4 rounded-full font-bold mr-2 hover:bg-gray-400 dark:hover:bg-gray-600">M</button>
                                <button className="bg-gray-300 dark:bg-gray-700 text-gray-700 dark:text-white py-2 px-4 rounded-full font-bold mr-2 hover:bg-gray-400 dark:hover:bg-gray-600">L</button>
                                <button className="bg-gray-300 dark:bg-gray-700 text-gray-700 dark:text-white py-2 px-4 rounded-full font-bold mr-2 hover:bg-gray-400 dark:hover:bg-gray-600">XL</button>
                                <button className="bg-gray-300 dark:bg-gray-700 text-gray-700 dark:text-white py-2 px-4 rounded-full font-bold mr-2 hover:bg-gray-400 dark:hover:bg-gray-600">XXL</button>
                            </div>
                        </div> */}
                        <div>
                            <span className="font-bold text-gray-700 dark:text-gray-300">Other</span>
                            <p className="text-gray-600 dark:text-gray-300 text-sm mt-2">
                                HOUSE-TRAINED
                               <br /> Yes
                                <br /> HEALTH
                                <br /> Vaccinations up to date, spayed / neutered.
                                <br /> GOOD IN A HOME WITH
                                <br /> Other dogs, children.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Pet