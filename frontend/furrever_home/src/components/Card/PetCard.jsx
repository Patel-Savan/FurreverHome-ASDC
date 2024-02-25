import React from 'react'

const PetCard = (
    type,
    breed,
    age,
    thumbnailSrc,
    thumbnailAlt = "Thumbnail",
    className,
    shelter,
    city,
    contact,
    id,
    handleClick
) => {
  return (
    <div className={`rounded-lg p-6 shadow-sm ${className}`} onClick={() => handleClick(id)}>
            <div className="overflow-hidden rounded-lg">
                <img
                    className="w-full cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-full h-auto"
                    src={thumbnailSrc}
                    alt={thumbnailAlt}
                />
            </div>
            <h5 className="pt-5 text-[14px] font-normal text-primary block">
                {type}
            </h5>
            <h3 className="w-full cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-lg h-auto">
                {breed},{age}
            </h3>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                Currently at {shelter}, {city} 
            </p>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                Want to adopt me ? contact at {contact}
            </p>
    </div>
  )
}

export default PetCard
