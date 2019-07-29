package by.vironit.training.basumatarau.messenger.integrationTest;

import by.vironit.training.basumatarau.messenger.MessengerApp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("integration-test")
@SpringBootTest(classes = {MessengerApp.class})
public class RestApiSecurityTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChainProxy;

    private MockMvc mockMvc;

    private static JSONObject userOne;
    private static JSONObject userTwo;
    private static JSONObject userThree;

    @BeforeClass
    public static void initMockData(){
        try {
            userOne =
                    new JSONObject()
                            .put("firstName", "Donald")
                            .put("lastName", "Duck")
                            .put("nickName", "MacDuck")
                            .put("email", "donald@mail.com")
                            .put("rawPassword", "stub");
            userTwo =
                    new JSONObject()
                            .put("firstName", "Penny")
                            .put("lastName", "Paw")
                            .put("nickName", "Kitty")
                            .put("email", "penny@mail.com")
                            .put("rawPassword", "stub");
            userThree =
                    new JSONObject()
                            .put("firstName", "Ted")
                            .put("lastName", "Malcolm")
                            .put("nickName", "Teddy")
                            .put("email", "ted@mail.com")
                            .put("rawPassword", "stub");
        } catch (JSONException e) {
            //to nothing
        }
    }

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

        return authResponse.andReturn().getResponse().getHeader("Authorization");
    }

    @Test
    public void whenSecurePointRequestedWithNoToken_thenUnauthorizedResponse() throws Exception {
        mockMvc.perform(get("/api/user/login")).andExpect(status().isUnauthorized());
    }

    @Test
    public void whenGivenValidToken_thenAuthorized() throws Exception {
        final String accessToken = obtainAccessToken("bad@mail.ru", "stub");
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

    @Test
    public void whenAuthenticatedUsersMakeConnections_thenConnectionsPersisted()
            throws Exception {

        mockMvc.perform(
                post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userOne.toString())
        ).andExpect(status().isOk());
        mockMvc.perform(
                post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userTwo.toString())
        ).andExpect(status().isOk());
        mockMvc.perform(
                post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userThree.toString())
        ).andExpect(status().isOk());

        final JSONObject userOneProfile =
                getUserProfileByEmail("donald@mail.com", "stub");
        final JSONObject userTwoProfile =
                getUserProfileByEmail("penny@mail.com", "stub");

        sendFriendRequest(userThree, userOneProfile);
        sendFriendRequest(userThree, userTwoProfile);

        final String userOneToken =
                obtainAccessToken(userThree.getString("email"), userThree.getString("rawPassword"));

        final String pendingContactsResponse = mockMvc.perform(
                get("/api/user/contacts/pending")
                        .header("Authorization", userOneToken)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(
                new JSONObject(pendingContactsResponse)
                        .getJSONArray("content")
                        .length()
        ).isEqualTo(2);
    }

    @Test
    public void whenAuthenticatedUsersConfirmConnections_thenConnectionsPersisted()
            throws Exception {

        confirmAllPendingContactsFriendRequest(userOne);
        confirmAllPendingContactsFriendRequest(userTwo);

        final String userThreeToken =
                obtainAccessToken(userThree.getString("email"), userThree.getString("rawPassword"));

        final String pendingContactsResponse = mockMvc.perform(
                get("/api/user/contacts")
                        .header("Authorization", userThreeToken)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(
                new JSONObject(pendingContactsResponse)
                        .getJSONArray("content")
                        .length()
        ).isEqualTo(2);
    }

    private void confirmAllPendingContactsFriendRequest(JSONObject user)
            throws Exception {
        final String token =
                obtainAccessToken(user.getString("email"), user.getString("rawPassword"));

        final String contactsJsonRespnse = mockMvc.perform(
                get("/api/user/contacts/pending")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        final JSONArray contacts = new JSONObject(contactsJsonRespnse)
                .getJSONArray("content");

        for (int i = 0; i < contacts.length(); i++) {
            final long contactId = contacts.getJSONObject(i).getLong("id");

            final String friendRequestUrl = String.format("/api/user/contact%d/confirm", contactId);
            mockMvc.perform(
                    put(friendRequestUrl).header("Authorization", token)
            ).andExpect(status().isOk());
        }
    }

    private void sendFriendRequest(JSONObject one, JSONObject two) throws Exception {
        final String userOneToken =
                obtainAccessToken(one.getString("email"), one.getString("rawPassword"));
        final String friendRequestUrl = String.format("/api/user/contact%d/request", two.getLong("id"));
        mockMvc.perform(
                put(friendRequestUrl).header("Authorization", userOneToken)
        ).andExpect(status().isOk());
    }

    private JSONObject getUserProfileByEmail(String email, String rawPassword) throws Exception {
        final String userOneToken = obtainAccessToken(email, rawPassword);
        final String userQueryJsonResponse = mockMvc.perform(
                get("/api/user/me")
                        .header("Authorization", userOneToken)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return new JSONObject(userQueryJsonResponse);
    }
}
