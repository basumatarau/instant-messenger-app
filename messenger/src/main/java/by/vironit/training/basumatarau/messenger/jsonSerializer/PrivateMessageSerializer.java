package by.vironit.training.basumatarau.messenger.jsonSerializer;

import by.vironit.training.basumatarau.messenger.dto.PrivateMessageDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class PrivateMessageSerializer extends JsonSerializer<PrivateMessageDto> {
    @Override
    public void serialize(PrivateMessageDto value,
                          JsonGenerator gen,
                          SerializerProvider serializers)
            throws IOException {
        gen.writeStartObject();
        gen.writeStringField("body", value.getBody());
        gen.writeEndObject();
    }
}
