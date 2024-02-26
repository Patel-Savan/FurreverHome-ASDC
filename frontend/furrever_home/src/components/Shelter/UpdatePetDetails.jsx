import React, { useState } from "react";
import {
    Button,
    Dialog,
    Card,
    CardHeader,
    CardBody,
    CardFooter,
    Typography,
    Input,
    Checkbox,
    
} from "@material-tailwind/react";
import axios from 'axios';
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../../utils/helper'
import { PencilIcon, TrashIcon } from "@heroicons/react/24/solid";

const UpdatePetDetails = ({pets,sid}) => {
    const [open, setOpen] = React.useState(false);
    const [response, setResponse] = useState({})
    const [loading, setLoading] = useState(true)
    const handleOpen = () => setOpen((cur) => !cur);
    const navigate = useNavigate();
    const token = readLocalStorage("token")
    console.log(pets)
    const [formData, setFormData] = useState({
        
        type: pets.type,
        breed: pets.breed,
        colour: pets.colour,
        gender: pets.gender,
        birthdate: pets.birthdate,
        petImage: pets.petImage,
        shelter:sid
    });

    const handleChange = (event) => {

        const newData = { ...formData }
        newData[event.target.id] = event.target.value

        setFormData(newData)
    }

    const handleImage = (image) => {

        const reader = new FileReader();
        reader.readAsDataURL(image);
        reader.onload = function (e) {
            //   console.log(e.target.result)
            const newData = { ...formData }
            newData.petImage = e.target.result
            setFormData(newData)
            //   console.log(typeof (image))
        };

        reader.onerror = function () {
            console.log(reader.error);
        };
    }

    // const token = readLocalStorage("token")

    const handleSubmit = (event) => {

        event.preventDefault();

        axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/shelter/editPet/${pets.petID}`, formData,{
            headers: {
                Authorization: `Bearer ${token}`,
              }
        })
            .then((res) => {
                console.log(res)
                setResponse(res)
                setLoading(true)
                toast.success("Pet Updated!");
                navigate(0)
                handleOpen();
                setFormData({
                    type: "",
                    breed: "",
                    colour: "",
                    gender: "",
                    birthdate: "",
                    petImage: ""
            })
                
            })
            .catch((err) => {
                console.log(err)
                toast.error(err.message)
                handleOpen();
                setFormData({
                        type: "",
                        breed: "",
                        colour: "",
                        gender: "",
                        birthdate: "",
                        petImage: ""
                })
            })
    }



    return (
        <>
            <button  onClick={handleOpen}><PencilIcon className="h-4 w-4"/></button>
            <Dialog
                size="lg"
                open={open}
                handler={handleOpen}
                className="bg-transparent shadow-none"
            >
                <Card className="mx-auto w-full max-w-[30rem] ">
                    <CardBody className="flex flex-col gap-4">
                        <Typography variant="h4" color="blue-gray">
                            Edit Pet Details
                        </Typography>

                        <form method="POST" onSubmit={handleSubmit}>

                       
                        <div>

                            <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                Type
                            </label>
                            <div className="mt-1">
                                <input
                                    id="type"
                                    name="type"
                                    type="text"
                                    value={formData.type}
                                    onChange={handleChange}
                                    autoComplete="text"
                                    required
                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                    placeholder='Enter Shelter Name'
                                />
                            </div>
                        </div>

                        <div>

                            <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                Breed
                            </label>
                            <div className="mt-1">
                                <input
                                    id="breed"
                                    name="breed"
                                    type="text"
                                    value={formData.breed}
                                    onChange={handleChange}
                                    autoComplete="text"
                                    required
                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                    placeholder='Enter Shelter Name'
                                />
                            </div>
                        </div>

                        <div>

                            <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                Colour
                            </label>
                            <div className="mt-1">
                                <input
                                    id="colour"
                                    name="colour"
                                    type="text"
                                    value={formData.colour}
                                    onChange={handleChange}
                                    autoComplete="text"
                                    required
                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                    placeholder='Enter Shelter Name'
                                />
                            </div>
                        </div>

                        <div>

                            <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                Gender
                            </label>
                            <div className="mt-1">
                                <input
                                    id="gender"
                                    name="gender"
                                    type="text"
                                    value={formData.gender}
                                    onChange={handleChange}
                                    autoComplete="text"
                                    required
                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                    placeholder='Enter Shelter Name'
                                />
                            </div>
                        </div>

                        <div>

                            <label htmlFor="shelterName" className="text-sm font-medium leading-6 text-gray-900 flex">
                                Birth Date
                            </label>
                            <div className="mt-1">
                                <input
                                    id="birthdate"
                                    name="birthdate"
                                    type="date"
                                    value={formData.birthdate}
                                    onChange={handleChange}
                                    autoComplete="text"
                                    required
                                    className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                    placeholder='Enter Shelter Name'
                                />
                            </div>
                        </div>

                        <div className="">
                            <label className="block text-sm font-medium leading-6 text-gray-900">Upload Image</label>
                            <input type="file"
                                name='petImage'
                                required
                                onChange={(event) => { handleImage(event.target.files[0]) }}
                                className="w-full text-black text-sm bg-white border file:cursor-pointer cursor-pointer file:border-0 file:py-2.5 file:px-4 file:bg-gray-100 file:hover:bg-gray-200 file:text-black rounded" />
                            <p className="text-xs text-gray-400 mt-2">PNG, JPG are Allowed.</p>
                        </div>
                        
                        <div className="pt-0 flex gap-4">
                        <button type="submit" className="btn btn-orange" variant="gradient" fullWidth>
                            Add
                        </button>
                        <button className="btn btn-orange" variant="gradient" onClick={handleOpen} fullWidth>
                            Close
                        </button>
                        </div>

                        
                        </form>

                    </CardBody>
                    
                    
                </Card>
                
            </Dialog>
        </>
    );
}

export default UpdatePetDetails