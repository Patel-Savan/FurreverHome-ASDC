import {
    Tab,
    TabPanel,
    Tabs,
    TabsBody,
    TabsHeader,
} from "@material-tailwind/react";
import React,{useState,useEffect} from "react";
import { readLocalStorage, saveLocalStorage } from '../../utils/helper';

import PetCard from "../../components/Card/PetCard";
// import pets from "../../dummydata/pets";
import axios from "axios";


const LostAndFoundHome = () => {
    const [activeTab, setActiveTab] = React.useState("1");
    const token = readLocalStorage("token")
    const [pets, setPets] = useState([]);
    const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`;
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        axios.get(`${baseurl}/petadopter/lostpets`,{
            headers: {
                Authorization: `Bearer ${token}`,
            }
        
        })
        .then((response) => {
            setPets(response.data);
            setLoading(true);
        });
    },[])


    return (
        <Tabs value={activeTab}>
            <TabsHeader
                className="rounded-none border-b border-blue-gray-50 bg-transparent m-10"
                indicatorProps={{
                    className:
                        "bg-transparent border-b-2 border-gray-900 shadow-none rounded-none",
                }}
            >

                <Tab
                    key="1"
                    value="1"
                    onClick={() => setActiveTab("1")}
                    className={activeTab === "1" ? "text-gray-900" : ""}
                >
                    Lost Pets
                </Tab>

                <Tab
                    key="2"
                    value="2"
                    onClick={() => setActiveTab("2")}
                    className={activeTab === "2" ? "text-gray-900" : ""}
                >
                    Your Request
                </Tab>

            </TabsHeader>
            <TabsBody>

                <TabPanel key="1" value="1">
                    <div className="grid gap-10 grid-cols-1 m-5 md:grid-cols-4 p-3 sm:p-8">
                        {
                            loading
                            ?
                            pets.map((pet) => {
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
                                    // onClick={handlePetClick}
                                    />)
                            })
                            :
                            <></>
                        }
                    </div>
                </TabPanel>

                <TabPanel key="2" value="2">
                    <div className="grid gap-10 grid-cols-1 m-5 md:grid-cols-4 p-3 sm:p-8">
                        {
                            loading
                            ?
                            pets.map((pet) => {
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
                                    // onClick={handlePetClick}
                                    />)
                            })
                            :
                            <></>
                        }
                    </div>
                </TabPanel>

            </TabsBody>
        </Tabs>
    );
}

export default LostAndFoundHome;