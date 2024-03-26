import React, { useState, useEffect } from 'react'
import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";
import { PencilIcon, TrashIcon } from "@heroicons/react/24/solid";
import {
    Card,
    CardHeader,
    Input,
    Typography,
    Button,
    CardBody,
    Chip,
    CardFooter,
    Tabs,
    TabsHeader,
    Tab,
    Avatar,
    IconButton,
    Tooltip,
} from "@material-tailwind/react";
import { DataGrid } from '@mui/x-data-grid';
import RegisterPet from './RegisterPet';
import UpdatePetDetails from './UpdatePetDetails';
import axios from 'axios';
import { toast } from "react-toastify";
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../../utils/helper'
import pet1 from "../../dummydata/pets"
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

const PetsTable = ({ pets }) => {

    const [search, setSearch] = useState('');


    const [loading, setLoading] = useState(false)
    const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`;
    const token = readLocalStorage("token")
    const sid = readLocalStorage("shelterID")
    const navigate = useNavigate()

    const handleChange = (e) => {
        setSearch(e.target.value)
    }



    const deletePet = (id) => {
        axios.delete(`${baseurl}/shelter/deletePet/${id}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
            .then(response => {
                setPets(response.data)
                setLoading(true)
                console.log(pets)

            })
            .catch(error => {
                console.log(error);
            })
    }

    // useEffect(() => {

    //     getPet()

    // }, [pets.length])

    const handlePetClick = (petId) => {
        navigate("/shelter/pet", {
            state: {
                id: petId
            }
        })
    }
    const columns = [
        { field: 'petID', headerName: 'PetID', width: 90 },
        {
            field: 'petImage',
            headerName: 'Pet',
            width: 150,
            editable: true,
            renderCell: (params) => <Avatar src={params.value} onClick={() => handlePetClick(params.row.petID)} />
        },
        {
            field: 'adopted',
            headerName: 'Status',
            width: 150,
            editable: true,
            renderCell: (param) => {
                return (
                    param.value ? <Chip color="green" value="Adopted" /> : <Chip color="cyan" value="Not Adopted" />
                )

            }
        },

        {
            field: 'type',
            headerName: 'Type',
            width: 110,
            editable: true,
        },
        {
            field: 'breed',
            headerName: 'Breed',
            width: 110,
            editable: true,
        },
        {
            field: 'colour',
            headerName: 'Colour',
            width: 110,
            editable: true,
        },
        {
            field: 'gender',
            headerName: 'Gender',
            width: 110,
            editable: true,
        },
        {
            field: 'birthdate',
            headerName: 'BirthDate',
            width: 110,
            editable: true,
        },
        {
            field: "",
            headerName: 'Action',
            width: 500,
            renderCell: (param) => {
                return (

                    <div className="flex gap-4">
                        <UpdatePetDetails pets={param.row} sid={sid} />
                        <button variant="text" onClick={() => { deletePet(param.row.petID) }}>
                            <TrashIcon className="h-4 w-4 text-red-600" />
                        </button>
                    </div>

                )

            }
        },
    ];


    return (
        <Card className=" h-[calc(100vh-2rem)]  mx-5 my-2">
            <CardHeader floated={false} shadow={false} className="rounded-none my-4">
                <div className="mb-8 flex items-center justify-between gap-8">
                    <div>
                        <Typography variant="h5" color="blue-gray">
                            Registered Pets
                        </Typography>
                        <Typography color="gray" className="mt-1 font-normal">
                            See information about all pets
                        </Typography>
                    </div>

                    <div className="flex shrink-0 flex-col gap-2 sm:flex-row">
                        {/* <Button variant="outlined" size="sm">
                            view all
                        </Button> */}
                        <Link to="/chat/shelter">
                        <button className="btn btn-orange m-5">Chat</button>
                        </Link>
                        
                        <RegisterPet />
                        {/* <Button className="flex items-center gap-3" size="sm">
                            <UserPlusIcon strokeWidth={2} className="h-4 w-4" /> Add member
                        </Button> */}
                    </div>
                </div>
                <div className="flex flex-col items-center justify-between gap-4 md:flex-row">
                    <div className="w-full md:w-72">
                        <Input
                            label="Search"
                            icon={<MagnifyingGlassIcon className="h-5 w-5" />}
                            value={search}
                            onChange={handleChange}
                        />
                    </div>
                </div>
            </CardHeader>


            <DataGrid
                rows={pets}
                getRowId={(row) => row.petID}
                columns={columns}
                initialState={{
                    pagination: {
                        paginationModel: {
                            pageSize: 10,
                        },
                    },
                }}
                pageSizeOptions={[10]}
                checkboxSelection
                disableRowSelectionOnClick

            />


        </Card>
    );
}

export default PetsTable