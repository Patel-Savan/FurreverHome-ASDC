import React,{useState} from 'react'
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


const columns = [
    { field: 'id', headerName: 'PetID', width: 90 },
    {
        field: 'petImage',
        headerName: 'Pet',
        width: 150,
        editable: true,
        renderCell: (params) => <Avatar src={params.value} />
    },
    {
        field: 'petStatus',
        headerName: 'Status',
        width: 150,
        editable: true,
        renderCell: (param) => {
            return(
                param?<Chip color="green" value="Adopted" />:<Chip color="cyan" value="blue" /> 
            )
        
    }
    },
    {
        field: 'age',
        headerName: 'Age',
        width: 150,
        editable: true,
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

        headerName: 'Action',
        width: 110,
        renderCell: () => {
            return (

                <div className="flex">
                    <IconButton variant="text">
                        <PencilIcon className="h-4 w-4" />
                    </IconButton>
                    <IconButton variant="text">
                        <TrashIcon className="h-4 w-4 text-red-600" />
                    </IconButton>
                </div>

            )

        }
    },
];







const ShelterTable = ({ shelters }) => {

    console.log(shelters)

    const [search, setSearch] = useState('');
    const handleChange = (e) => {
        setSearch(e.target.value)
    }

    return (
        <Card className=" h-[calc(100vh-2rem)]  mx-5 my-2">
            <CardHeader floated={false} shadow={false} className="rounded-none my-4">
                <div className="mb-8 flex items-center justify-between gap-8">
                    <div>
                        <Typography variant="h5" color="blue-gray">
                            Shelter Requests
                        </Typography>
                    </div>
                    <div className="flex shrink-0 flex-col gap-2 sm:flex-row">

                    </div>
                </div>
                <div className="flex flex-col items-center justify-between gap-4 md:flex-row">
                    <div className="w-full md:w-72">
                        <Input
                            label="Search"
                            icon={<MagnifyingGlassIcon className="h-5 w-5" />}
                            value={search }
                            onChange={handleChange}
                        />
                    </div>
                </div>
            </CardHeader>

            {/* <Card className=""> */}
            <DataGrid
                rows={shelters}
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

export default ShelterTable