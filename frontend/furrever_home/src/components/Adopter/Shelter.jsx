import React,{useState} from 'react'
import { useParams } from 'react-router-dom';
import axios from 'axios';
import PetCard from '../../components/Card/ShelterCard'
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../../utils/helper'
import pets from '../../dummydata/pets';

const Shelter = () => {

    const { sid } = useParams();
    // const [data,setData] = useState(pets)
    const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`;
    const token = readLocalStorage("token")

    
        // axios.get(`${baseurl}/shelter/${sid}/pets`, {
        //     headers: {
        //         Authorization: `Bearer ${token}`,
        //     }
        // })
        //     .then(response => {
        //         setData(response.data)
        //         console.log(data);
        //         setLoading(true)
        //         console.log(pets)
    
        //     })
        //     .catch(error => {
        //         console.log(error);
        //     })


    return (

        // <div className="bg-cream py-8">
        //     <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        //         <div className="flex flex-col md:flex-row -mx-4">
        //             <div className="md:flex-1 px-4">
        //                 <div className="h-[460px] rounded-lg bg-gray-300 dark:bg-gray-700 mb-4">
        //                     <img className="w-full h-full object-cover" src="https://cdn.pixabay.com/photo/2020/05/22/17/53/mockup-5206355_960_720.jpg" alt="Product Image" />
        //                 </div>
        //                 <div className="flex -mx-2 mb-4">
        //                     <div className="w-1/2 px-2">
        //                         <button className="w-full bg-gray-900 dark:bg-gray-600 text-white py-2 px-4 rounded-full font-bold hover:bg-gray-800 dark:hover:bg-gray-700">Add to Cart</button>
        //                     </div>
        //                     <div className="w-1/2 px-2">
        //                         <button className="w-full bg-gray-200 dark:bg-gray-700 text-gray-800 dark:text-white py-2 px-4 rounded-full font-bold hover:bg-gray-300 dark:hover:bg-gray-600">Add to Wishlist</button>
        //                     </div>
        //                 </div>
        //             </div>
        //             <div className="md:flex-1 px-4">
        //                 <div>
        //                     <div class="bg-white">
        //                         <div class="mx-auto max-w-2xl px-4 py-16 sm:px-6 sm:py-24 lg:max-w-7xl lg:px-8">
        //                             <h2 class="text-2xl font-bold tracking-tight text-gray-900">Customers also purchased</h2>

        //                             <div class="mt-6 grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8">
        //                                 <div class="group relative">
        //                                     <div class="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-md bg-gray-200 lg:aspect-none group-hover:opacity-75 lg:h-80">
        //                                         <img src="https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg" alt="Front of men&#039;s Basic Tee in black." class="h-full w-full object-cover object-center lg:h-full lg:w-full"/>
        //                                     </div>
        //                                     <div class="mt-4 flex justify-between">
        //                                         <div>
        //                                             <h3 class="text-sm text-gray-700">
        //                                                 <a href="#">
        //                                                     <span aria-hidden="true" class="absolute inset-0"></span>
        //                                                     Basic Tee
        //                                                 </a>
        //                                             </h3>
        //                                             <p class="mt-1 text-sm text-gray-500">Black</p>
        //                                         </div>
        //                                         <p class="text-sm font-medium text-gray-900">$35</p>
        //                                     </div>
        //                                 </div>

                                    
        //                             </div>
        //                         </div>
        //                     </div>
        //                 </div>
        //             </div>
        //         </div>
        //     </div>
        // </div>
        <div className="bg-cream">
    <div className="container mx-auto py-8">
        <div className="grid grid-cols-4 sm:grid-cols-12 gap-6 px-4">
            <div className="col-span-4 sm:col-span-3">
                <div className="bg-white shadow rounded-lg p-6">
                    <div className="flex flex-col items-center">
                        <img src="" className="w-32 h-32 bg-gray-300 rounded-full mb-4 shrink-0">

                        </img>
                        <h1 className="text-xl font-bold">Shelter Name</h1>
                        {/* <p className="text-gray-700">Capacity: {user.capacity}</p> */}
                        <div className="mt-6 flex flex-wrap gap-4 justify-center">
                        <button className='btn btn-primary'>Contact</button>
                        </div>
                    </div>
                    <hr className="my-6 border-t border-gray-300"/>
                    <div className="flex flex-col">
                        <span className="text-gray-700 uppercase tracking-wider mb-2">Address</span>
                        <ul>
                            <li className="mb-2">Address: Address</li>
                            <li className="mb-2">City: City</li>
                            <li className="mb-2">Country: Country</li>
                            <li className="mb-2">Zipcode: Zipcode</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div className="col-span-4 sm:col-span-9">
                <div className="bg-white shadow rounded-lg p-6">
                    <h2 className="text-xl font-bold mb-4">Available Pets</h2>
                    <div className="grid gap-8 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 p-3 sm:p-8">
                    {
                        pets.map((pet) => {
                            return (              
                              <PetCard
                                key={pet.petId}
                                className="bg-[#f3faff]"
                                type={pet.type}
                                breed={pet.breed}
                                age={pet.age}
                                thumbnailSrc={pet.petImage}
                                shelterName={pet.shelterName}
                                shelterCity={pet.shelterCity}
                                shelterContact={pet.shelterContact}
                                petId={pet.petId}
                                // onClick={handlePetClick}
                              />)
                          })
                    }
                    </div>

                    
                </div>
            </div>
        </div>
    </div>
</div>

    )
}

export default Shelter