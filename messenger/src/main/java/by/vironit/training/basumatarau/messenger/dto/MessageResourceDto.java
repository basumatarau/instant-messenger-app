package by.vironit.training.basumatarau.messenger.dto;

import javax.validation.constraints.NotNull;

public class MessageResourceDto {

    public enum MessageResourceType {
        FILE, IMAGE
    }

    @NotNull
    private MessageResourceDto.MessageResourceType type;

    @NotNull
    private String resourceName;

    @NotNull
    private byte[] binData;

    public MessageResourceType getType() {
        return type;
    }

    public void setType(MessageResourceType type) {
        this.type = type;
    }

    public byte[] getBinData() {
        return binData;
    }

    public void setBinData(byte[] binData) {
        this.binData = binData;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public MessageResourceDto() {
    }

    public MessageResourceDto(@NotNull MessageResourceDto.MessageResourceType resource,
                              @NotNull String resourceName,
                              @NotNull byte[] binData) {
        this.type = resource;
        this.resourceName = resourceName;
        this.binData = binData;
    }
}
