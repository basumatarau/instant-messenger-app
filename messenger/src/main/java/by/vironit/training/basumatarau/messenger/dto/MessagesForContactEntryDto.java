package by.vironit.training.basumatarau.messenger.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;

public class MessagesForContactEntryDto {
    private final LinkedList<MessageDto> messages;
    private final boolean isFirst;
    private final boolean isLast;

    @JsonCreator
    public MessagesForContactEntryDto(@JsonProperty("messages") LinkedList<MessageDto> messages,
                                      @JsonProperty("isFirstSlice") boolean isFirstSlice,
                                      @JsonProperty("isLastSlice") boolean isLastSlice) {
        this.messages = messages;
        this.isFirst = isFirstSlice;
        this.isLast = isLastSlice;
    }

    public LinkedList<MessageDto> getMessages() {
        return messages;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public boolean isLast() {
        return isLast;
    }
}
