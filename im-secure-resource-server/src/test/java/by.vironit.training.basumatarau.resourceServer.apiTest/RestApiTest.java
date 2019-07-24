package by.vironit.training.basumatarau.resourceServer.apiTest;

import by.vironit.training.basumatarau.resourceServer.ResourceServerStarter;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {ResourceServerStarter.class})
public class RestApiTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChainProxy;

    private MockMvc mockMvc;

    @Before
    public void init(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webAppContext)
                .addFilter(this.springSecurityFilterChainProxy)
                .build();
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("username", username);
        httpHeaders.add("password", password);

        final ResultActions authResponse = mockMvc.perform(
                get("/api/user/login")
                        .headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        final String bearerTokenResponse =
                authResponse.andReturn().getResponse().getHeader("Authorization");
        return bearerTokenResponse;
    }

    @Test
    public void whenSecurePointRequestedWithNoToken_thenUnauthorizedResponse() throws Exception {
        mockMvc.perform(get("/api/user/info")).andExpect(status().isUnauthorized());
    }

    @Test
    public void whenGivenValidToken_thenAuthorized() throws Exception {
        final String accessToken = obtainAccessToken("black@mail.gov", "stub");
        System.out.println("token:" + accessToken);
        mockMvc.perform(
                get("/api/user/contacts")
                        .header("Authorization", accessToken)
        ).andExpect(status().isOk());
    }

    @Test
    public void whenNewAccountCreated_thenNewUserCanAuthorize() throws Exception {
        final JSONObject newAccJson =
                new JSONObject()
                .put("firstName", "John")
                .put("lastName", "Doe")
                .put("nickName", "Jhonny")
                .put("email", "doe@mail.com")
                .put("rawPassword", "stub");
        mockMvc.perform(
                post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(newAccJson.toString())
        ).andExpect(status().isOk());


        final String accessToken = obtainAccessToken("doe@mail.com", "stub");
        System.out.println("token:" + accessToken);

        mockMvc.perform(
                get("/api/user/contacts")
                        .header("Authorization", accessToken)
        ).andExpect(status().isOk());
    }
}
