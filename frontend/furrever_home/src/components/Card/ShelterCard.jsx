import React from 'react'

const ShelterCard = ({
    heading,
    city,
    thumbnailSrc,
    thumbnailAlt = "Thumbnail",
    className,
    capacity,
    contact,
    id
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
        </div>
    )
}

export default ShelterCard


// function Card3Presentation() {
//     return (
//         <div className="grid gap-8 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 p-3 sm:p-8">
//             <Card3
//                 className="bg-[#fcf4ff]"
//                 heading="Heading"
//                 description="Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam euismod volutpat."
//                 thumbnailSrc="/img/placeholder-1.jpg"
//             />
//             <Card3
//                 className="bg-[#fefaf0]"
//                 heading="Heading"
//                 description="Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam euismod volutpat."
//                 thumbnailSrc="/img/placeholder-1.jpg"
//             />
//             <Card3
//                 className="bg-[#f3faff]"
//                 heading="Heading"
//                 description="Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam euismod volutpat."
//                 thumbnailSrc="/img/placeholder-1.jpg"
//             />
//         </div>
//     );
// }

// export { Card3Presentation };
