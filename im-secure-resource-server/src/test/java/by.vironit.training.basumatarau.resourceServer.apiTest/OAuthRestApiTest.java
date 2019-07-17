package by.vironit.training.basumatarau.resourceServer.apiTest;

import by.vironit.training.basumatarau.resourceServer.ResourceServerStarter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ResourceServerStarter.class})
public class OAuthRestApiTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChainProxy;

    private static final String CLIENT_ID = "myTrustedClient";
    //private static final String CLIENT_SECRET = "secret";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String CLIENT_SECRET = "secret";

    private MockMvc mockMvc;

    @Before
    public void init(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webAppContext)
                .addFilter(this.springSecurityFilterChainProxy)
                .build();
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        final LinkedMultiValueMap<String, String> params
                = new LinkedMultiValueMap<>();

        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", passwordEncoder.encode(CLIENT_SECRET));
        params.add("username", username);
        params.add("password", password);

        final ResultActions authResponse = mockMvc.perform(
                post("/oauth/token")
                        .params(params)
                        .with(httpBasic(CLIENT_ID, CLIENT_SECRET)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE));

        final String authResponseBody =
                authResponse
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        final JacksonJsonParser jsonParser = new JacksonJsonParser();

        return jsonParser.parseMap(authResponseBody).get("access_token").toString();
    }

    @Test
    public void whenSequrePointRequestedWithNoToken_thenUnauthorizedResponse() throws Exception {
        mockMvc.perform(get("/api/user/info")).andExpect(status().isUnauthorized());
    }

    @Test
    public void whenGivenValidToken_thenAuthorized() throws Exception {
        final String accessToken = obtainAccessToken("black@mail.gov", "stub");
        System.out.println("token:" + accessToken);
        mockMvc.perform(
                get("/api/user/info")
                        .param("access_token", accessToken)
                        .with(httpBasic("black@mail.gov", "stub"))
        ).andExpect(status().isOk());
    }
}
