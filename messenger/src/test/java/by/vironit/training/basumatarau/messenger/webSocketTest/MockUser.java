package by.vironit.training.basumatarau.messenger.webSocketTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;

public class MockUser {
    private final SockJsClient sockJsClient;
    private final WebSocketStompClient stompClient;
    private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    private MockMvc mockMvc;

    public MockUser(SockJsClient sockJsClient,
                    WebSocketStompClient stompClient,
                    MockMvc mockMvc) {
        this.sockJsClient = sockJsClient;
        this.stompClient = stompClient;
        this.mockMvc = mockMvc;
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }

    public WebSocketHttpHeaders getHeaders() {
        return headers;
    }

    public WebSocketStompClient getStompClient() {
        return stompClient;
    }
}
