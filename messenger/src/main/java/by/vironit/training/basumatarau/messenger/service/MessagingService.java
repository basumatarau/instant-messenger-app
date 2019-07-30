package by.vironit.training.basumatarau.messenger.service;

import by.vironit.training.basumatarau.messenger.dto.*;

public interface MessagingService {
    void sendPrivateMessage(
            IncomingMessageDto messageDto,
            PersonalContactVo personalContactVo
    ) throws InstantiationException;

    void sendDistributedMessage(
            IncomingMessageDto messageDto,
            SubscriptionVo subscriptionVo
    ) throws InstantiationException;
}
