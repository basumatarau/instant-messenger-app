package by.vironit.training.basumatarau.messenger.api;

import by.vironit.training.basumatarau.messenger.dto.ContactEntryVo;
import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.messenger.dto.UserProfileDto;
import by.vironit.training.basumatarau.messenger.service.ContactService;
import by.vironit.training.basumatarau.messenger.service.UserService;
import by.vironit.training.basumatarau.messenger.service.util.MessagingServiceVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private MessagingServiceVisitor messagingServiceVisitorForContactEntries;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public IncomingMessageDto handleSimpleGreeting(IncomingMessageDto greeting){
        return new IncomingMessageDto(greeting.getBody());
    }

    @MessageMapping("/messaging")
    public void handleUserUpdate(
            @Valid @Payload IncomingMessageDto msg,
            Principal principal) throws InstantiationException {

        final UserProfileDto currentUser =
                userService.getUserProfileByUserEmail(principal.getName());

        final ContactEntryVo contactEntry =
                contactService.getContactEntryForUserByEntryId(
                        msg.getContactEntryId(), currentUser.getId()
                );

        contactEntry.accept(messagingServiceVisitorForContactEntries, msg);
    }
}
