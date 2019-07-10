package by.vironit.training.basumatarau.messengerService.socket;

import by.vironit.training.basumatarau.messengerService.dto.IncomingMessageDto;
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
        return s.trim().length() > 0 && s.trim().length() < 1000;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
