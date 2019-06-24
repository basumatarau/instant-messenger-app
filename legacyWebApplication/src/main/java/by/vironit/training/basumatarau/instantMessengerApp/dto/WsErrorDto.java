package by.vironit.training.basumatarau.instantMessengerApp.dto;

public class WsErrorDto {
    private final String error;

    public WsErrorDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
