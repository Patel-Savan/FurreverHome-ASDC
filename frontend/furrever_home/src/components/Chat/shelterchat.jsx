import axios from "axios";
import React, { useEffect, useState } from "react";
import { StreamChat } from "stream-chat";
import { deleteLocalStorage, readLocalStorage, saveLocalStorage } from '../../utils/helper'
import { useParams } from 'react-router-dom';
import {
  Chat,
  Channel,
  ChannelHeader,
  ChannelList,
  LoadingIndicator,
  MessageInput,
  VirtualizedMessageList,
  Thread,
  Window,
} from "stream-chat-react";

import "stream-chat-react/dist/css/index.css";
// http://localhost:8080/api/chats/from/3/to/2



// const filters = { type: "messaging", members: { $in: ["little-wood-9"] } };
// // TS tweak No1
// const sort = { last_message_at: -1 } as const;

const App = () => {

    const userid = readLocalStorage("id")
    const token = readLocalStorage("token")
    const User = JSON.parse(readLocalStorage("User"))

    const [data,setData] = useState(null)
    const [loading,setLoading] = useState(false)

    const { shelterId } = useParams();

    // useEffect(()=>{
    //     // axios.get(`http://localhost:8080/api/chats/from/${userid}/to/${id}`)
    //     // .then()

       
    // },[])

    // Assuming you fetch these values from the backend somehow
// const userToken = token; // from backend
// const apiKey = data.apikey; // from backend
// const userId = data.userChatId; // from backend
// const channelId = data.channelId; // from backend
  // TS tweak No2
  const [chatClient, setChatClient] = useState(null);
  const [activeChannel, setActiveChannel] = useState(null);
  // Filter channels by tag
  const filters = {
    type: "messaging",
    // Assuming 'tag' is a custom field you've set on the channel metadata
    petadopter: User.email,
  };

  const initChat = async (data) => {
    const client = StreamChat.getInstance(data.apiKey);

    await client.connectUser(
      {
        id: data.userChatId,
        name: "Hola",
        image:
          "http://www.gravatar.com/avatar/01844c0abfb31c1c86ed54e30c81c267.jpg?s=50&d=identicon",
      },
      data.token
    );

    setChatClient(client);
    // Join the channel by ID
    const channel = client.channel("messaging", data.channelId);

    // To watch the channel (start receiving events)
    await channel.watch();

    setChatClient(client);
    setActiveChannel(channel);
  };

  useEffect(() => {
    axios.get(`http://localhost:8080/api/chats/from/${userid}/to/${shelterId}`,{
        headers: {
          Authorization: `Bearer ${token}`,
        }
      })
    .then(response => {
        console.log(response.data)
        // setData(response.data)       
        return response.data   
    })
    .then(data=> {
        
        initChat(data)
    })
    .catch(error => {
        console.log(error);
    })
    // initChat();
    // Make sure to disconnect the user when the component unmounts
    return () => {
      if (chatClient) {
        chatClient.disconnectUser();
      }
    };
  }, []);

  if (!chatClient || !activeChannel) {
    return <LoadingIndicator />;
  }

  return (

    loading
    ?
    <></>
    :
    <Chat client={chatClient}>
      {/* Use filters to only show channels with the specified tag */}
      <ChannelList
        filters={filters}
        sort={{}}
        setActiveChannel={setActiveChannel}
      />
      {activeChannel && (
        <Channel channel={activeChannel}>
          <Window>
            <ChannelHeader />
            <VirtualizedMessageList />
            <MessageInput />
          </Window>
          <Thread />
        </Channel>
      )}
    </Chat>
    
  );
};

export default App;
