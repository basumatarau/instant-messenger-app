package by.vironit.training.basumatarau.resourceServer.jsonSerializer;

import by.vironit.training.basumatarau.messengerService.dto.PersonalContactVo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class PersonalContactVoSerializer extends JsonSerializer<PersonalContactVo> {
    @Override
    public void serialize(PersonalContactVo personalContactVo,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", "personal_contact");
        jsonGenerator.writeNumberField("id", personalContactVo.getId());
        jsonGenerator.writeObjectField("owner", personalContactVo.getOwner());
        jsonGenerator.writeObjectField("person", personalContactVo.getPerson());
        jsonGenerator.writeEndObject();
    }
}
