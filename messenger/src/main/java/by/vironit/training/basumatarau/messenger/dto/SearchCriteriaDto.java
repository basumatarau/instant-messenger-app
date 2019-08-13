package by.vironit.training.basumatarau.messenger.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class SearchCriteriaDto {
    @NotNull
    private final String key;
    @NotNull
    private final Object value;

    @JsonCreator
    public SearchCriteriaDto(
            @JsonProperty("key") String key,
            @JsonProperty("value") Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
