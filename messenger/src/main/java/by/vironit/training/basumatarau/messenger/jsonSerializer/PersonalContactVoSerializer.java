package by.vironit.training.basumatarau.messenger.jsonSerializer;

import by.vironit.training.basumatarau.messenger.dto.PersonalContactVo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

@JsonComponent
public class PersonalContactVoSerializer extends JsonSerializer<PersonalContactVo> {

    @Override
    public void serializeWithType(PersonalContactVo value,
                                  JsonGenerator gen,
                                  SerializerProvider serializers,
                                  TypeSerializer typeSer)
            throws IOException {
        //super.serializeWithType(value, gen, serializers, typeSer);
        WritableTypeId typeId = typeSer.typeId(value, START_OBJECT);
        typeSer.writeTypePrefix(gen, typeId);
        serialize(value, gen, serializers);
        typeSer.writeTypeSuffix(gen, typeId);
    }

    @Override
    public void serialize(PersonalContactVo personalContactVo,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeNumberField("id", personalContactVo.getId());
        jsonGenerator.writeObjectField("owner", personalContactVo.getOwner());
        jsonGenerator.writeObjectField("person", personalContactVo.getPerson());
        jsonGenerator.writeObjectField("confirmed", personalContactVo.getConfirmed());
    }
}
