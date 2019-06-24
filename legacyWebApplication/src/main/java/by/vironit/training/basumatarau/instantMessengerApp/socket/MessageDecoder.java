package by.vironit.training.basumatarau.instantMessengerApp.socket;

import by.vironit.training.basumatarau.instantMessengerApp.dto.IncomingMessageDto;
import com.google.gson.Gson;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<IncomingMessageDto> {

    private static Gson gson = new Gson();

    @Override
    public IncomingMessageDto decode(String s) throws DecodeException {
        return gson.fromJson(s, IncomingMessageDto.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s.trim().length() > 0;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
