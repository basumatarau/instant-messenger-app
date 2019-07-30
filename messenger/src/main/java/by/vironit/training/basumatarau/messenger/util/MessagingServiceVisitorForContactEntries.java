package by.vironit.training.basumatarau.messenger.util;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.messenger.dto.PersonalContactVo;
import by.vironit.training.basumatarau.messenger.dto.SubscriptionVo;
import by.vironit.training.basumatarau.messenger.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagingServiceVisitorForContactEntries {
    @Autowired
    private MessagingService messagingService;

    public void visit(IncomingMessageDto msg, PersonalContactVo personalContactVo)
            throws InstantiationException {
        messagingService.sendPrivateMessage(msg, personalContactVo);
    }

    public void visit(IncomingMessageDto msg, SubscriptionVo subscriptionVo)
            throws InstantiationException {
        messagingService.sendDistributedMessage(msg, subscriptionVo);
    }
}
