package by.vironit.training.basumatarau.messenger.webSocketTest.util;

import by.vironit.training.basumatarau.messenger.dto.MessageDto;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public abstract class MessageReceivedCallback implements StompFrameHandler {

    private final StompSession session;
    private final String incomingMessageBody;
    private final AtomicReference<Throwable> failure;
    private final CountDownLatch deliveryLatch;

    public MessageReceivedCallback(StompSession stompSession,
                                   AtomicReference<Throwable> failure,
                                   String incomingMessageBody,
                                   CountDownLatch deliveryLatch){
        this.session = stompSession;
        this.failure = failure;
        this.incomingMessageBody = incomingMessageBody;
        this.deliveryLatch = deliveryLatch;
    }

    public abstract void receiverEcho();

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageDto.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        MessageDto message = (MessageDto) payload;
        try {
            assertEquals(incomingMessageBody, message.getBody());
            System.out.println("-#########- Anticipated message received! -#########-");
            receiverEcho();

            System.out.println(message);
        } catch (Throwable t) {
            failure.set(t);
        } finally {
            session.disconnect();
            deliveryLatch.countDown();
        }
    }
}
