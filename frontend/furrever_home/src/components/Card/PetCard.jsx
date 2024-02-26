import React from 'react'

const PetCard = ( {
    className,
    type,
    breed,
    age,
    thumbnailSrc,
    shelterName,
    shelterCity,
    shelterContact,
    petId
}) => {

    const thumbnailAlt = "Alternate Image"
  return (
    <div className={`rounded-lg p-6 shadow-sm ${className}`}>
            <div className="overflow-hidden rounded-lg">
                <img
                    className="w-full cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-full h-auto"
                    src={thumbnailSrc}
                    alt={thumbnailAlt}
                />
            </div>
            <h3 className="pt-5 text-primary block">
                {type}
            </h3>
            <h5 className="w-full text-[14px] cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-lg h-auto">
                {breed}
            </h5>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                {shelterName} 
            </p>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                Want to adopt me ? contact at {shelterContact}
            </p>
    </div>
  )
}

export default PetCard
