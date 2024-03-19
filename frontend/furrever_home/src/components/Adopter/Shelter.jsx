import React, { useState,useEffect } from 'react'
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import PetCard from '../../components/Card/ShelterCard'
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../../utils/helper'
import { Link } from 'react-router-dom';


const Shelter = () => {

    const { id } = useParams()
    const {state} = useLocation()
    console.log(state)
    const [pets,setPets] = useState({})
    const [loading,setLoading] = useState(false)
    const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`
    const token = readLocalStorage("token")
    const navigate = useNavigate()

    const handlePetClick = (petId) => {
        navigate("/adopter/pet",{
          state:{
            id:petId
          }
        })
      }

      useEffect(() => {
        axios.get(`${baseurl}/petadopter/${state.userId}/pets`, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
            .then(response => {
                setPets(response.data)
                console.log(response.data);
                setLoading(true)
                console.log(pets)
                console.log("HIIII")
                console.log(pets)
    
            })
            .catch(error => {
                console.log(error);
            })
        
      }, []);
    


    

    return (

        <div className="">
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
                                    <Link to={`/chat/${id}`}>
                                        <button className='btn btn-primary'>Chat</button>
                                    </Link>
                                </div>
                            </div>
                            <hr className="my-6 border-t border-gray-300" />
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
                                    loading 
                                    ?
                                    pets.map((pet) => {
                                        return (
                                            <PetCard
                                                key={pet.petID}
                                                className="bg-[#f3faff]"
                                                type={pet.type}
                                                breed={pet.breed}
                                                age={pet.age}
                                                thumbnailSrc={pet.petImage}
                                                shelterName={pet.shelterName}
                                                shelterCity={pet.shelterCity}
                                                shelterContact={pet.shelterContact}
                                                petId={pet.petId}
                                                handleClick={()=>navigate("/adopter/pet",{
                                                    state:{
                                                      id:pet.petID
                                                    }
                                                  })}
                                            />)
                                          
                                    })
                                    :
                                        <h1>Loading</h1>  
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