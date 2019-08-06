package by.vironit.training.basumatarau.messenger.webSocketTest;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.messenger.webSocketTest.util.MessageReceiverTestHandler;
import by.vironit.training.basumatarau.messenger.webSocketTest.util.MessageSenderTestHandler;
import by.vironit.training.basumatarau.messenger.webSocketTest.util.MockUser;
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
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("ws-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistributedMessagingWebSocketTest {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String STOMP_ENDPOINT = "ws://localhost:{port}/api/WSUpgrade";

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChainProxy;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private MockUser sender;
    private List<MockUser> receivers = new LinkedList<>();

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webAppContext)
                .addFilter(this.springSecurityFilterChainProxy)
                .build();

        final MappingJackson2MessageConverter messageConverter =
                new MappingJackson2MessageConverter();
        messageConverter.setPrettyPrint(true);
        messageConverter.setObjectMapper(objectMapper);

        sender = new MockUser(messageConverter, port);
        receivers.add(new MockUser(messageConverter, port));
        receivers.add(new MockUser(messageConverter, port));
    }

    @Test
    public void getDistributedMessage() throws Exception {

        final CountDownLatch deliveryLatch = new CountDownLatch(1 + receivers.size());
        final CountDownLatch subscriptionLatch = new CountDownLatch(1 + receivers.size());

        final AtomicReference<Throwable> failure = new AtomicReference<>();

        final LinkedList<String> accessTokens = new LinkedList<>();
        accessTokens.add(obtainAccessToken("bad@mail.ru", "stub", mockMvc));
        accessTokens.add(obtainAccessToken("black@mail.gov", "stub", mockMvc));
        accessTokens.add(obtainAccessToken("doe@mail.com", "stub", mockMvc));

        final IncomingMessageDto testMessage = new IncomingMessageDto(3L, "Hi everybody!");

        sender.getHeaders().add(AUTHORIZATION_HEADER_NAME, accessTokens.remove());
        sender.performStompClientConnectWithHandler(
                STOMP_ENDPOINT,
                new MessageSenderTestHandler(
                        failure,
                        deliveryLatch,
                        subscriptionLatch,
                        testMessage
                )
        );

        receivers.forEach(r->{
            r.getHeaders().add(AUTHORIZATION_HEADER_NAME, accessTokens.remove());
            r.performStompClientConnectWithHandler(
                    STOMP_ENDPOINT,
                    new MessageReceiverTestHandler(
                            failure,
                            deliveryLatch,
                            subscriptionLatch,
                            testMessage.getBody()
                    )
            );
        });



        if (deliveryLatch.await(500, TimeUnit.SECONDS)) {
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
