package by.vironit.training.basumatarau.messenger.jsonSerializer;

import by.vironit.training.basumatarau.messenger.dto.PrivateMessageDto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class PrivateMessageDeserializer extends JsonDeserializer<PrivateMessageDto> {
    @Override
    public PrivateMessageDto deserialize(JsonParser p,
                                         DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        final TreeNode treeNode = p.getCodec().readTree(p);
        final String body = ((TextNode) treeNode.get("body")).textValue();

        return new PrivateMessageDto(body);
    }
}
