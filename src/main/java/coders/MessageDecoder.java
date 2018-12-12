package coders;

import messages.ContentMessage;
import messages.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message> {
    @Override
    public Message decode(String msg){
        Message message = null;
        if(willDecode(msg)) {
            JsonObject obj = Json.createReader(new StringReader(msg)).readObject();
            String action = obj.getString("action");
            try{
                switch(action) {
                    case "content":
                        message = new ContentMessage(obj.getString("content"));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
