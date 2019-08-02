package by.vironit.training.basumatarau.messenger.jsonSerializer;

import by.vironit.training.basumatarau.messenger.dto.SubscriptionVo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

@JsonComponent
public class SubscriptionVoSerializer extends JsonSerializer<SubscriptionVo> {

    @Override
    public void serializeWithType(SubscriptionVo value,
                                  JsonGenerator gen,
                                  SerializerProvider serializers,
                                  TypeSerializer typeSer)
            throws IOException {

        WritableTypeId typeId = typeSer.typeId(value, START_OBJECT);
        typeSer.writeTypePrefix(gen, typeId);
        serialize(value, gen, serializers);
        typeSer.writeTypeSuffix(gen, typeId);
    }

    @Override
    public void serialize(SubscriptionVo subscriptionVo,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStringField("type", "subscription");
        jsonGenerator.writeNumberField("id", subscriptionVo.getId());
        jsonGenerator.writeObjectField("owner", subscriptionVo.getOwner());
        jsonGenerator.writeObjectField("chat_room", subscriptionVo.getChatRoom());
    }
}
