package by.vironit.training.basumatarau.messenger.webSocketTest;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class PrivateMessageReceiverTestHandler extends StompSessionHandlerAdapter {

    private final AtomicReference<Throwable> failure;
    private final CountDownLatch testTimeoutLatch;
    private final CountDownLatch subscriptionLatch;
    private final IncomingMessageDto testIncomingMessage;

    public PrivateMessageReceiverTestHandler(AtomicReference<Throwable> reference,
                                           CountDownLatch timeoutLatch,
                                           CountDownLatch subscriptionLatch,
                                           IncomingMessageDto testIncomingMessage) {
        this.failure = reference;
        this.testTimeoutLatch = timeoutLatch;
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
                        testIncomingMessage,
                        testTimeoutLatch
                ){
                    @Override
                    public void signallingMessage() {
                        System.out.println("receiver got message");
                    }
                }
        );

        subscriptionLatch.countDown();


        try {
            if(subscriptionLatch.await(500, TimeUnit.SECONDS)) {
                if(failure.get() != null) {
                    throw new RuntimeException("failed to subscribe all the receivers");
                }
            }
        } catch (Throwable t) {
            failure.set(t);
            testTimeoutLatch.countDown();
        }
    }
}
