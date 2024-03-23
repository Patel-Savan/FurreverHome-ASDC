import React from 'react'
import { Link } from 'react-router-dom'

const ShelterCard = ({
    heading,
    city,
    thumbnailSrc,
    thumbnailAlt = "Thumbnail",
    className,
    capacity,
    contact,
    userId,
    shelterId,
    handleClick
}) => {
    return (
        <div className={`rounded-lg p-6 shadow-sm ${className}`}>
            <div className="overflow-hidden rounded-lg">
                <img
                    className="w-full cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-lg h-auto"
                    src={thumbnailSrc}
                    alt={thumbnailAlt}
                />
            </div>
            <h5 className="pt-5 text-[14px] font-normal text-primary block">
                {heading}
            </h5>
            <h3 className="w-full cursor-pointer transition duration-200 ease-in-out transform hover:scale-110 rounded-lg h-auto">
            {city}
            </h3>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                Capacity of {capacity} 
            </p>
            <p className="font-normal text-gray-500 cursor-pointer text-lg duration-300 transition hover:text-[#FA5252] mt-2">
                Contact : {contact}
            </p>
            {/* <Link to="/adopter/shelter/1"> */}
            <button className='btn btn-outline' onClick={()=>handleClick(shelterId,userId)}>
                Details
            </button>
            {/* </Link> */}
        </div>
    )
}

export default ShelterCard

