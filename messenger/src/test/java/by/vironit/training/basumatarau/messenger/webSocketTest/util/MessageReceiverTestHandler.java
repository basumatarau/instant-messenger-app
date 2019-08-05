package by.vironit.training.basumatarau.messenger.webSocketTest.util;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class MessageReceiverTestHandler extends StompSessionHandlerAdapter {

    private final AtomicReference<Throwable> failure;
    private final CountDownLatch deliveryLatch;
    private final CountDownLatch subscriptionLatch;
    private final String testIncomingMessageBody;

    public MessageReceiverTestHandler(AtomicReference<Throwable> reference,
                                      CountDownLatch deliveryLatch,
                                      CountDownLatch subscriptionLatch,
                                      String testIncomingMessageBody) {
        this.failure = reference;
        this.deliveryLatch = deliveryLatch;
        this.subscriptionLatch = subscriptionLatch;
        this.testIncomingMessageBody = testIncomingMessageBody;
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
                        testIncomingMessageBody,
                        deliveryLatch
                ){
                    @Override
                    public void receiverEcho() {
                        System.out.println("Receiver got message");
                    }
                }
        );

        subscriptionLatch.countDown();
    }
}
