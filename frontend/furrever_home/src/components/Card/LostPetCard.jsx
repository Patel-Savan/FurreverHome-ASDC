import React from 'react'
import UpdateLostPet from "../LostAndFound/UpdateLostPet";
const PetCard = ({
    className,
    type,
    breed,
    colour,
    gender,
    phone,
    email,
    petImage,
    petId
}) => {

    const pet = {
        type,
        breed,
        colour,
        gender,
        phone,
        email,
        petImage,
        petId
    }
    console.log(petId)
    const thumbnailAlt = "Alternate Image"
    return (
        <div className={`rounded-lg p-6 shadow-sm ${className}`}>
            <div className="overflow-hidden rounded-lg">
                <img
                    className="w-full cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-full h-auto"
                    src={petImage}
                    alt={petImage}
                />
            </div>
            <h3 className="pt-5 text-primary block">
                {type}
            </h3>
            <h5 className="w-full text-[14px] cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-lg h-auto">
                {breed}
            </h5>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                Email:  {email}
            </p>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                Phone:  {phone}
            </p>
            <UpdateLostPet pets={pet} />
        </div>
    )
}

export default PetCard
