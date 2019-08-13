package by.vironit.training.basumatarau.messenger.service.util;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;

public interface VisitableContactEntryVo
        <T extends MessagingServiceVisitor,
        R extends IncomingMessageDto> {
    void accept(T visitor, R msg) throws InstantiationException;
}
