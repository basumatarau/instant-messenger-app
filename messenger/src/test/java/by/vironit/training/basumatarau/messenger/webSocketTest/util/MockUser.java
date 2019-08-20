package by.vironit.training.basumatarau.messenger.webSocketTest.util;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.util.ArrayList;
import java.util.List;

public class MockUser {
    private final SockJsClient sockJsClient;
    private final WebSocketStompClient stompClient;
    private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    private final int port;

    public MockUser(MappingJackson2MessageConverter messageConverter, int connectionPort) {

        port = connectionPort;

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxBinaryMessageBufferSize(32768);
        container.setDefaultMaxTextMessageBufferSize(32768);

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient(container)));

        this.sockJsClient = new SockJsClient(transports);
        this.stompClient = new WebSocketStompClient(this.sockJsClient);
        this.stompClient.setMessageConverter(messageConverter);
    }

    public void performStompClientConnectWithHandler(String endpoint,
                                                     StompSessionHandlerAdapter handlerAdapter){
        this.stompClient.connect(
                endpoint,
                this.headers,
                handlerAdapter,
                this.port
        );
    }

    public WebSocketHttpHeaders getHeaders() {
        return headers;
    }

    public WebSocketStompClient getStompClient() {
        return stompClient;
    }
}
