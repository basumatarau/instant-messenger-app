package by.vironit.training.basumatarau.messenger.jsonSerializer;

import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.IOException;

@JsonComponent
public class PageOfUserProfilesSerializer
        extends JsonSerializer<Page<UserProfileDto>> {
    @Override
    public void serialize(Page<UserProfileDto> page,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeArrayFieldStart("content");
        for (UserProfileDto userProfileDto : page.getContent()) {
            jsonGenerator.writeObject(userProfileDto);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeBooleanField("first", page.isFirst());
        jsonGenerator.writeBooleanField("last", page.isLast());
        jsonGenerator.writeNumberField("totalPages", page.getTotalPages());
        jsonGenerator.writeNumberField("totalElements", page.getTotalElements());
        jsonGenerator.writeNumberField("numberOfElements", page.getNumberOfElements());
        jsonGenerator.writeNumberField("size", page.getSize());
        jsonGenerator.writeNumberField("number", page.getNumber());
        Sort sort = page.getSort();
        jsonGenerator.writeArrayFieldStart("sort");
        for (Sort.Order order : sort) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("property", order.getProperty());
            jsonGenerator.writeStringField("direction", order.getDirection().name());
            jsonGenerator.writeBooleanField("ignoreCase", order.isIgnoreCase());
            jsonGenerator.writeStringField("nullHandling", order.getNullHandling().name());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
