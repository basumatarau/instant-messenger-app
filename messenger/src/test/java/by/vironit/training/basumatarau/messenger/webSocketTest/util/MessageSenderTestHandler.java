package by.vironit.training.basumatarau.messenger.webSocketTest.util;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;
import org.springframework.messaging.simp.stomp.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class MessageSenderTestHandler extends StompSessionHandlerAdapter {

    private final AtomicReference<Throwable> failure;
    private final CountDownLatch deliveryLatch;
    private final CountDownLatch subscriptionLatch;
    private final IncomingMessageDto testIncomingMessage;

    public MessageSenderTestHandler(AtomicReference<Throwable> reference,
                                    CountDownLatch deliveryLatch,
                                    CountDownLatch subscriptionLatch,
                                    IncomingMessageDto testIncomingMessage) {
        this.failure = reference;
        this.deliveryLatch = deliveryLatch;
        this.subscriptionLatch = subscriptionLatch;
        this.testIncomingMessage = testIncomingMessage;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        this.failure.set(new Exception(headers.toString()));
    }

    @Override
    public void handleException(StompSession s,
                                StompCommand c,
                                StompHeaders h,
                                byte[] p,
                                Throwable ex) {
        this.failure.set(ex);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable ex) {
        this.failure.set(ex);
    }

    @Override
    public void afterConnected(final StompSession session, StompHeaders connectedHeaders) {

        session.subscribe(
                "/user/queue",
                new MessageReceivedCallback(
                        session,
                        failure,
                        testIncomingMessage.getBody(),
                        deliveryLatch
                ){
                    @Override
                    public void receiverEcho() {
                        System.out.println("Sender got his message back");
                    }
                }
        );

        subscriptionLatch.countDown();

        try {
            if(subscriptionLatch.await(500, TimeUnit.SECONDS)) {
                if(failure.get() != null) {
                    throw new RuntimeException("failed to subscribe all the receivers", failure.get());
                }
            }
            session.send("/app/messaging", testIncomingMessage);
        } catch (Throwable t) {
            failure.set(t);
            deliveryLatch.countDown();
        }
    }
}
