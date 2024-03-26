import axios from "axios";
import React, { useEffect, useState } from "react";
import { StreamChat } from "stream-chat";
import {
  Channel,
  ChannelHeader,
  ChannelList,
  Chat,
  LoadingIndicator,
  MessageInput,
  Thread,
  VirtualizedMessageList,
  Window,
} from "stream-chat-react";
import { readLocalStorage, saveLocalStorage } from '../../utils/helper';

import "stream-chat-react/dist/css/index.css";




const ShelterChat = () => {
  // TS tweak No2
  const [chatClient, setChatClient] = useState(null);
  const [shelter, setShelter] = useState({})

  const sort = { last_message_at: -1 };

  const userid = readLocalStorage("id")
  const token = readLocalStorage("token")
  const User = JSON.parse(readLocalStorage("User"))
  const baseurl = `${import.meta.env.VITE_BACKEND_BASE_URL}`

  console.log(User)

  const filters = {
    type: "messaging",
    // Assuming 'tag' is a custom field you've set on the channel metadata
    shelter: User.email
  };

  const initChat = async (data) => {
    const client = StreamChat.getInstance(data.apiKey);
    console.log(data)
    await client.connectUser(
      {
        id: data.userChatId,
        name: data.userChatId,
        image:
          "https://www.gravatar.com/avatar/e1f4d0f8f39124293034fded1762ffe0.jpg?s=50&d=identicon",
      },
      data.token
    );

    setChatClient(client);
  };


  useEffect(() => {

    axios.get(`${import.meta.env.VITE_BACKEND_BASE_URL}/shelter/single/${userid}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        console.log(response.data)
        saveLocalStorage("User", JSON.stringify(response.data));
        setLoading(true)
      })
      .catch(error => {
        console.log(error);
      })

    axios.get(`http://localhost:8080/api/chats/history/${userid}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    })
      .then(response => {
        console.log(response.data)
        // setData(response.data)       
        return response.data
      })
      .then(data => {

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

  if (!chatClient) {
    return <LoadingIndicator />;
  }

  return (
    <Chat client={chatClient}>
      <ChannelList filters={filters} sort={sort} />
      <Channel>
        <Window>
          <ChannelHeader />
          <VirtualizedMessageList
            additionalVirtuosoProps={{
              increaseViewportBy: { top: 400, bottom: 200 },
            }}
          />
          <MessageInput />
        </Window>
        <Thread />
      </Channel>
    </Chat>
  );
};

export default ShelterChat;
