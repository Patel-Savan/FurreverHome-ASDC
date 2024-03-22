import React, { useEffect, useState } from "react";
import { StreamChat } from "stream-chat";
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

const userToken =
  "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoidGVzdHNoZWx0ZXJ1c2VyMSIsImV4cCI6MTcxMTA1OTgzNSwiaWF0IjoxNzExMDU2MjMwLCJpc3MiOiJTdHJlYW0gQ2hhdCBKYXZhIFNESyIsInN1YiI6IlN0cmVhbSBDaGF0IEphdmEgU0RLIn0.gkQ4uLv33RQogVDogEgxThiT7bThmcaskOgTW0ijBIQ";

const filters = {
  type: "messaging",
  // Assuming 'tag' is a custom field you've set on the channel metadata
  petadopter: "shelter@yopmail.com",
};
// TS tweak No1
const sort = { last_message_at: -1 };

const App = () => {
  // TS tweak No2
  const [chatClient, setChatClient] = useState(null);

  useEffect(() => {
    const initChat = async () => {
      const client = StreamChat.getInstance("vr5er8x4n4kc");

      await client.connectUser(
        {
          id: "testshelteruser1",
          name: "Shelter1",
          image:
            "https://www.gravatar.com/avatar/e1f4d0f8f39124293034fded1762ffe0.jpg?s=50&d=identicon",
        },
        userToken
      );

      setChatClient(client);
    };

    initChat();
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

export default App;
