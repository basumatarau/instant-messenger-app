package by.vironit.training.basumatarau.messenger.webSocketTest;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.messenger.dto.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("ws-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrivateMessagingWebSocketTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChainProxy;

    @Autowired
    private ObjectMapper objectMapper;

    private MockUser sender;

    private MockUser receiver;

    @Before
    public void setup() {

        final MappingJackson2MessageConverter messageConverterOne =
                new MappingJackson2MessageConverter();
        messageConverterOne.setPrettyPrint(true);
        messageConverterOne.setObjectMapper(objectMapper);

        final MappingJackson2MessageConverter messageConverterTwo =
                new MappingJackson2MessageConverter();
        messageConverterOne.setPrettyPrint(true);
        messageConverterOne.setObjectMapper(objectMapper);

        final MockMvc mockMvcOne = MockMvcBuilders
                .webAppContextSetup(this.webAppContext)
                .addFilter(this.springSecurityFilterChainProxy)
                .build();

        final MockMvc mockMvcTwo = MockMvcBuilders
                .webAppContextSetup(this.webAppContext)
                .addFilter(this.springSecurityFilterChainProxy)
                .build();

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        final SockJsClient senderSockJsClient = new SockJsClient(transports);

        final WebSocketStompClient senderWebSocketStompClient =
                new WebSocketStompClient(senderSockJsClient);
        senderWebSocketStompClient.setMessageConverter(messageConverterOne);

        final SockJsClient receiverSockJsClient = new SockJsClient(transports);

        final WebSocketStompClient receiverWebSocketStompClient =
                new WebSocketStompClient(receiverSockJsClient);
        senderWebSocketStompClient.setMessageConverter(messageConverterTwo);

        sender = new MockUser(
                senderSockJsClient,
                senderWebSocketStompClient,
                mockMvcOne
        );

        receiver = new MockUser(
                receiverSockJsClient,
                receiverWebSocketStompClient,
                mockMvcTwo
        );
    }

    @Test
    public void getPrivateMessage() throws Exception {

        final CountDownLatch testTimeoutLatch = new CountDownLatch(2);
        final CountDownLatch subscriptionLatch = new CountDownLatch(2);

        final AtomicReference<Throwable> failure = new AtomicReference<>();

        final String accessTokenOne = obtainAccessToken("bad@mail.ru", "stub", sender.getMockMvc());
        final String accessTokenTwo = obtainAccessToken("black@mail.gov", "stub", receiver.getMockMvc());
        final IncomingMessageDto testIncomingMessageDto = new IncomingMessageDto(1L, "Hello World!");

        StompSessionHandler senderHandler = new PrivateMessageSenderTestHandler(
                failure,
                testTimeoutLatch,
                subscriptionLatch,
                testIncomingMessageDto
        );

        PrivateMessageReceiverTestHandler receiverHandler = new PrivateMessageReceiverTestHandler(
                failure,
                testTimeoutLatch,
                subscriptionLatch,
                testIncomingMessageDto
        );

        sender.getHeaders().add("Authorization", accessTokenOne);
        sender.getStompClient().connect("ws://localhost:{port}/api/WSUpgrade",
                sender.getHeaders(),
                senderHandler,
                this.port);

        receiver.getHeaders().add("Authorization", accessTokenTwo);
        receiver.getStompClient().connect("ws://localhost:{port}/api/WSUpgrade",
                receiver.getHeaders(),
                receiverHandler,
                this.port);


        if (testTimeoutLatch.await(500, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                throw new AssertionError("", failure.get());
            }
        } else {
            fail("Private message has not been received by all the intended receivers");
        }
    }

    private String obtainAccessToken(String username, String password, MockMvc mockMvc) throws Exception {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("username", username);
        httpHeaders.add("password", password);

        final ResultActions authResponse = mockMvc.perform(
                get("/api/user/login")
                        .headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        return authResponse.andReturn().getResponse().getHeader("Authorization");
    }
}