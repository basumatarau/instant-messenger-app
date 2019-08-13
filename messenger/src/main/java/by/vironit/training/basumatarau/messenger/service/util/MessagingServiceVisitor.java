package by.vironit.training.basumatarau.messenger.service.util;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.messenger.dto.PersonalContactVo;
import by.vironit.training.basumatarau.messenger.dto.SubscriptionVo;

public interface MessagingServiceVisitor {
    void visit(IncomingMessageDto msg,
               PersonalContactVo personalContactVo) throws InstantiationException;

    void visit(IncomingMessageDto msg,
               SubscriptionVo subscriptionVo) throws InstantiationException;
}
