package by.vironit.training.basumatarau.instantMessengerApp.validator;


import by.vironit.training.basumatarau.instantMessengerApp.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.instantMessengerApp.exception.ValidationException;

public class IncomingMessageValidator {
    public static void validate(IncomingMessageDto message) throws ValidationException {
        if(message.getBody().length() > 500){
            throw new ValidationException("incoming message is to big");
        }
    }
}
