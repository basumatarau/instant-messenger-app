package by.vironit.training.basumatarau.messenger.webSocketTest;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.messenger.dto.MessageDto;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class MessageReceivedCallback implements StompFrameHandler {

    private final StompSession session;
    private final IncomingMessageDto incomingMessage;
    private final AtomicReference<Throwable> failure;
    private final CountDownLatch timeoutLatch;

    public MessageReceivedCallback(StompSession stompSession,
                                   AtomicReference<Throwable> failure,
                                   IncomingMessageDto incomingMessage,
                                   CountDownLatch latch){
        this.session = stompSession;
        this.failure = failure;
        this.incomingMessage = incomingMessage;
        this.timeoutLatch = latch;
    }

    public void signallingMessage(){
        System.out.println("default");
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageDto.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        MessageDto message = (MessageDto) payload;
        try {
            assertEquals(incomingMessage.getBody(), message.getBody());
            System.out.println("-#########- Anticipated message received! -#########-");
            signallingMessage();
            System.out.println(message);
        } catch (Throwable t) {
            failure.set(t);
        } finally {
            session.disconnect();
            timeoutLatch.countDown();
        }
    }
}
