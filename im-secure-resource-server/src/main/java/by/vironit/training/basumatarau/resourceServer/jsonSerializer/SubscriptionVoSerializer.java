package by.vironit.training.basumatarau.resourceServer.jsonSerializer;

import by.vironit.training.basumatarau.messengerService.dto.SubscriptionVo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class SubscriptionVoSerializer extends JsonSerializer<SubscriptionVo> {
    @Override
    public void serialize(SubscriptionVo subscriptionVo,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", "subscription");
        jsonGenerator.writeNumberField("id", subscriptionVo.getId());
        jsonGenerator.writeObjectField("owner", subscriptionVo.getOwner());
        jsonGenerator.writeObjectField("chat_room", subscriptionVo.getChatRoom());
        jsonGenerator.writeEndObject();
    }
}
