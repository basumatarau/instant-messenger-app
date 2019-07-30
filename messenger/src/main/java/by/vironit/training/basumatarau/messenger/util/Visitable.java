package by.vironit.training.basumatarau.messenger.util;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;

public interface Visitable<T, R extends IncomingMessageDto> {
    void accept(T visitor, R msg) throws InstantiationException;
}
