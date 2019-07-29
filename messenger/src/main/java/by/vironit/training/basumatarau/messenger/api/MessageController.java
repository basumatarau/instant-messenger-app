package by.vironit.training.basumatarau.messenger.api;

import by.vironit.training.basumatarau.messenger.dto.PrivateMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public PrivateMessageDto handleSimpleGreeting(PrivateMessageDto greeting){
        return new PrivateMessageDto(greeting.getBody());
    }
}
