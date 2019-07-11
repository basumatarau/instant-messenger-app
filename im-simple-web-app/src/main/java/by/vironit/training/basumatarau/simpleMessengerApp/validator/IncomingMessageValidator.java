package by.vironit.training.basumatarau.simpleMessengerApp.validator;


import by.vironit.training.basumatarau.simpleMessengerApp.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.simpleMessengerApp.exception.ValidationException;

public class IncomingMessageValidator {
    public static void validate(IncomingMessageDto message) throws ValidationException {
        if(message.getBody().length() > 500){
            throw new ValidationException("incoming message is to big");
        }
    }
}
