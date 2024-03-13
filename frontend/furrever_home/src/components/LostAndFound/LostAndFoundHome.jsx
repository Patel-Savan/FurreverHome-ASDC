import React from "react";
import {
    Tabs,
    TabsHeader,
    TabsBody,
    Tab,
    TabPanel,
} from "@material-tailwind/react";

import pets from "../../dummydata/pets"
import PetCard from "../../components/Card/PetCard"
 

const LostAndFoundHome = ()=>{
    const [activeTab, setActiveTab] = React.useState("1");
    
    return (
        <Tabs value={activeTab}>
            <TabsHeader
                className="rounded-none border-b border-blue-gray-50 bg-transparent p-0"
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
                    <div className="grid gap-8 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 p-3 sm:p-8">
                    {
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
                    }
                    </div>
                    </TabPanel>
         
            </TabsBody>
        </Tabs>
    );
}

export default LostAndFoundHome;