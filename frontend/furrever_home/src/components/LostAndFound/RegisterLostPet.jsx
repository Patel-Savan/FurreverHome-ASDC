import {
    Card,
    CardBody,
    Dialog,
    Typography
} from "@material-tailwind/react";
import axios from 'axios';
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { readLocalStorage } from '../../utils/helper';

const RegisterLostPet = ({setChange}) => {
    const [open, setOpen] = React.useState(false);
    const [response, setResponse] = useState({})
    const [loading, setLoading] = useState(true)
    const handleOpen = () => setOpen((cur) => !cur);
    const navigate = useNavigate();
    const sid = readLocalStorage("shelterID");
    const token = readLocalStorage("token")
    console.log("Bearer " + token)
    const [image, setPetImage] = useState("");

    const [formData, setFormData] = useState({
        type: "dog",
        breed: "dobarman",
        colour: "black",
        gender: "male",
        phone: "78688659959",
        email: "pateljay15082@gmail.com",
        petImage: "data:image/jpeg;base64,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"
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
            console.log(e.target.result)

            setPetImage(e.target.result)
            //   console.log(typeof (image))
        };

        reader.onerror = function () {
            console.log(reader.error);
        };
    }



    const handleSubmit = (event) => {

        event.preventDefault();

        const data = {
            type: "dog",
            breed: "dobarman",
            colour: "black",
            gender: "male",
            phone: "78688659959",
            email: "pateljay15082@gmail.com",
            petImage: ""
        }
        console.log(data)

        axios.post(`${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter/lostpet`, data, {
            headers: {
                Authorization: "Bearer " + token,
            }
        })
            .then((res) => {
                console.log(res)
                setChange(true)
                setResponse(res)
                setLoading(true)
                toast.success("New Pet added!");
                // navigate(0)
                handleOpen();
                setFormData({
                    type: "dog",
                    breed: "dobarman",
                    colour: "black",
                    gender: "male",
                    phone: "78688659959",
                    email: "pateljay15082@gmail.com",
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
                    phone: "",
                    email: "",
                    petImage: ""
                })
            })
    }



    return (
        <>
            <button className="btn btn-orange m-5" onClick={handleOpen}>Add new Pet</button>
            <Dialog
                size="lg"
                open={open}
                handler={handleOpen}
                className="bg-transparent shadow-none"
            >
                <Card className="mx-auto w-full max-w-[30rem] ">
                    <CardBody className="flex flex-col gap-4">
                        <Typography variant="h4" color="blue-gray">
                            Add Lost Pet
                        </Typography>
                        <Typography
                            className="mb-3 font-normal"
                            variant="paragraph"
                            color="gray"
                        >
                            Enter details of new pet
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
                                        value={formData.name}
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

export default RegisterLostPet