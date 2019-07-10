package by.vironit.training.basumatarau.instantMessengerApp.socket;

import by.vironit.training.basumatarau.instantMessengerApp.dto.MessageDto;
import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<MessageDto> {

    private static Gson gson = new Gson();

    @Override
    public String encode(MessageDto message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
