package by.vironit.training.basumatarau.messenger.webSocketTest;

import by.vironit.training.basumatarau.messenger.dto.IncomingMessageDto;
import by.vironit.training.basumatarau.messenger.dto.MessageResourceDto;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
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
public class PrivateMessageWithResourcesWebSocketTest {

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
    private MockUser receiver;

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
        receiver = new MockUser(messageConverter, port);
    }

    @Test
    public void getPrivateMessage() throws Exception {

        final CountDownLatch deliveryLatch = new CountDownLatch(2);
        final CountDownLatch subscriptionLatch = new CountDownLatch(2);

        final AtomicReference<Throwable> failure = new AtomicReference<>();

        final String accessTokenOne = obtainAccessToken("bad@mail.ru", "stub", mockMvc);
        final String accessTokenTwo = obtainAccessToken("black@mail.gov", "stub", mockMvc);
        final IncomingMessageDto testMessage =
                new IncomingMessageDto(1L, "Hello World!");

        //testMessage.setResources();
        final MessageResourceDto testMessageResource = new MessageResourceDto();
        testMessageResource.setType(MessageResourceDto.MessageResourceType.IMAGE);

        final URI uri
                = getClass()
                .getResource("/test-image.jpg")
                .toURI();

        final File imageFile = new File(uri);
        final BufferedImage image = ImageIO.read(imageFile);

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();

        testMessageResource.setBinData(byteArrayOutputStream.toByteArray());
        testMessageResource.setResourceName("test-image(some-ape).jpg");

        testMessage.setResources(new MessageResourceDto[]{testMessageResource});

        MessageSenderTestHandler senderHandler = new MessageSenderTestHandler(
                failure,
                deliveryLatch,
                subscriptionLatch,
                testMessage
        );

        MessageReceiverTestHandler receiverHandler = new MessageReceiverTestHandler(
                failure,
                deliveryLatch,
                subscriptionLatch,
                testMessage.getBody()
        );

        sender.getHeaders().add(AUTHORIZATION_HEADER_NAME, accessTokenOne);
        sender.performStompClientConnectWithHandler(
                STOMP_ENDPOINT,
                senderHandler
        );

        receiver.getHeaders().add(AUTHORIZATION_HEADER_NAME, accessTokenTwo);
        receiver.performStompClientConnectWithHandler(
                STOMP_ENDPOINT,
                receiverHandler
        );

        //Thread.sleep(10000);
        if (deliveryLatch.await(500, TimeUnit.SECONDS)) {
            if (failure.get() != null) {
                throw new AssertionError("", failure.get());
            }
        } else {
            fail("Private message has not been received by all the intended receivers");
        }
    }

    private String obtainAccessToken(String username,
                                     String password,
                                     MockMvc mockMvc) throws Exception {
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