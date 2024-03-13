import React, { useEffect, useState } from 'react'
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../utils/helper'
import { useNavigate } from 'react-router-dom'
import ShelterCard from '../components/Card/ShelterCard'
import PetCard from '../components/Card/PetCard'
import {
  Card,
  Typography,
  List,
  ListItem,
  ListItemPrefix,
} from "@material-tailwind/react";
import {
  UserCircleIcon,
  Cog6ToothIcon,
  InboxIcon,
  PowerIcon,
} from "@heroicons/react/24/solid";
import axios from 'axios'
import { Link } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

const PetAdopterHome = () => {

  const navigate = useNavigate();
  const [data, setData] = useState([]);
  const [filter, setFilter] = useState("Shelter");
  const [searchQuery, setSearchQuery] = useState("");
  const [type, setType] = useState("city");
  const [userCity, setUserCity] = useState("");
  const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}/petadopter`;
  const token = readLocalStorage("token")
  const id = readLocalStorage("id");
  const [open, setOpen] = React.useState(0);

  const handleOpen = (value) => {
    setOpen(open === value ? 0 : value);
  };

  useEffect(() => {

    axios.get(`${baseurl}/${id}`, {
      headers: {
        Authorization: `Bearer ${token} `,
      }
    })
      .then(response => {
        console.log(response.data)
        saveLocalStorage("User", JSON.stringify(response.data));
        setSearchQuery(response.data.city)
        // console.log(filter + "=" + searchQuery);
        setUserCity(response.data.city)
        return response.data.city
      })
      .then((city) => {
        console.log(city)
        axios.post(`${baseurl}/searchshelter`, {
          city: city
        }, {
          headers: {
            Authorization: `Bearer ${token} `,
          }
        })
          .then(response => {
            setData(response.data.shelterResponseDtoList)
            console.log(response.data)
          })
          .catch(error => {
            toast.error("Cannot load data")
          })
      })
      .catch(error => {
        toast.error("Cannot get User details");
      })

  }, [])


  const handleTypeChange = (event) => {
    setType(event.target.value);
  }

  const handleSearch = (event) => {
    event.preventDefault();

    if (filter === "Shelter") {

      if (type === "All") {

        axios.get(`${baseurl}/shelters`, {
          headers: {
            Authorization: `Bearer ${token} `,
          }
        })
          .then(response => {
            setData(response.data)
            console.log(response.data)
          })
          .catch(error => {
            toast.error("Cannot get All Shelters")
          })

      }
      else {

        axios.post(`${baseurl}/searchshelter`, {
          [type]: searchQuery
        }, {
          headers: {
            Authorization: `Bearer ${token} `,
          }
        })
          .then(response => {
            setData(response.data.shelterResponseDtoList)
            console.log(response.data.shelterResponseDtoList)
          })
          .catch(error => {
            toast.error("Cannot load data")
          })

        console.log(type + "=" + searchQuery)

      }
    }
    else {

      axios.post(`${baseurl}/searchpet`, {
        [type]: searchQuery
      }, {
        headers: {
          Authorization: `Bearer ${token} `,
        }
      })
        .then(response => {
          setData(response.data.petResponseDtoList)
          console.log(response.data.petResponseDtoList)
        })
        .catch(error => {
          toast.error("Cannot load data")
        })
      console.log(type + "=" + searchQuery)

    }
  }

  useEffect(() => {
    if (filter === "Shelter") {

      axios.post(`${baseurl}/searchshelter`, {
        city: userCity
      }, {
        headers: {
          Authorization: `Bearer ${token} `,
        }
      })
        .then(response => {
          setData(response.data.shelterResponseDtoList)
          console.log(response.data)
        })
        .catch(error => {
          toast.error("Cannot load data")
        })

    }
    else {

      axios.post(`${baseurl}/searchpet`, {
        type: "dog"
      }, {
        headers: {
          Authorization: `Bearer ${token} `,
        }
      })
        .then(response => {
          setData(response.data.petResponseDtoList)
          console.log(response.data.petResponseDtoList)
        })
        .catch(error => {
          toast.error("Cannot load data")
        })

    }
  }, [filter])

  const handleFilterChange = (event) => {
    if (event.target.value === "Pet") {
      setType("age")
      setSearchQuery("")
    } else {
      setType("city")
      setSearchQuery("")
    }
    setFilter(event.target.value);
  }

  const handleSearchChange = (event) => {
    setSearchQuery(event.target.value);
  }

  const handleShelterClick = (shelterId) => {
    navigate(`/adopter/shelter/${shelterId}`,{
      state:{
        id:shelterId
      }
    })
  }

  const handlePetClick = (petId) => {
    navigate("/adopter/pet",{
      state:{
        id:petId
      }
    })
  }

  return (

    <section className='lg:flex'>

      <Card className="lg:h-[calc(100vh-2rem)] sm:w-full  lg:max-w-[20rem] m-2 p-4 bg-primary shadow-xl shadow-white-900/5 ">
        
         <div className='flex align-middle justify-center mt-2'>
          <form onSubmit={handleSearch} className='flex-col container mx-4'>
            <input className=' rounded-2xl flex m-4' type="text" onChange={handleSearchChange} />
            <select
              className=' rounded-2xl flex mx-4'
              name="filter"
              value={filter}
              onChange={handleFilterChange}>
              <option value="Shelter">Shelter</option>
              <option value="Pet">Pet</option>
            </select>

            {filter === "Shelter" && (
              <select
                className=' rounded-2xl flex m-4'
                name="type"
                onChange={handleTypeChange}>
                <option value="city">City</option>
                <option value="name">Name</option>
                <option value="capacity">Capacity</option>
                <option value="All">All</option>
              </select>
            )}
            {filter === "Pet" && (
              <select
                className=' rounded-2xl flex m-4'
                name="type"
                onChange={handleTypeChange}>
                <option value="age">Age</option>
                <option value="breed">Breed</option>
                <option value="type">Type</option>
                <option value="gender">Gender</option>
                <option value="color">Color</option>
              </select>
            )}
            <button
              type="submit"
              className="btn btn-outline align-middle justify-center flex mx-4 bg-orange-500 hover:bg-orange-800"
            >
              Search
            </button>
          </form>
        </div>

      </Card>
      <div>

       

        <div className="grid gap-8 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 p-3 sm:p-8">
          {/* <Card
          className="bg-[#fcf4ff]"
          heading="Heading"
          description="Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam euismod volutpat."
          thumbnailSrc={shelter_image}
        />
        <Card
          className="bg-[#fefaf0]"
          heading="Heading"
          description="Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam euismod volutpat."
          thumbnailSrc={shelter_image}
        />
        <Card
          className="bg-[#f3faff]"
          heading="Heading"
          description="Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam euismod volutpat."
          thumbnailSrc={shelter_image}
        /> */}

          {filter === "Shelter" &&
            data
              .map((shelter) => {
                return (
                  <ShelterCard
                    className="bg-[#f3faff]"
                    heading={shelter.name}
                    city={shelter.city}
                    thumbnailSrc={shelter.image}
                    capacity={shelter.capacity}
                    contact={shelter.contact}
                    key={shelter.id}
                    shelterId={shelter.id}
                    handleClick={handleShelterClick}
                  />
                )
              })}
          {filter === "Pet" &&
            data.map((pet) => {
              return (

                <PetCard
                  key={pet.petId}
                  className="bg-[#f3faff]"
                  type={pet.type}
                  breed={pet.breed}
                  age={pet.age}
                  thumbnailSrc={pet.petImage}
                  shelterName={pet.shelterName}
                  shelterCity={pet.shelterCity}
                  shelterContact={pet.shelterContact}
                  petId={pet.petId}
                  handleClick={handlePetClick}
                />)
            })
          }
        </div>
      </div>

    </section>
  )
}

export default PetAdopterHome
