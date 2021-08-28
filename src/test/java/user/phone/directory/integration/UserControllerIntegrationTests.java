package user.phone.directory.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.phone.directory.UserPhoneDirectoryApplication;
import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.request.UserRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.dto.response.UserResponse;
import com.user.phone.directory.enums.PhoneEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserPhoneDirectoryApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerIntegrationTests {

  @Autowired
  private MockMvc mockMvc;

  ObjectMapper mapper = new ObjectMapper();

  private UserRequest userRequest;

  private PhoneRequest phoneRequest;

  @BeforeEach
  public void setup() {
    userRequest = UserRequest.builder().userName("Cisco").emailAddress("cisco@gmail.com")
        .preferredPhoneNumber("123").build();

    phoneRequest = PhoneRequest.builder().phoneModel(PhoneEnum.IPHONE)
        .phoneName("Test").phoneNumber("12344").build();
  }


  @WithMockUser(username = "user", password = "password")
  @Test
  public void createUser_success() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse response = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    assertEquals("Cisco", response.getUserName());
    assertEquals("cisco@gmail.com", response.getEmailAddress());
    assertEquals("123", response.getPreferredPhoneNumber());
    assertNotNull(response.getUserId());
    assertNotNull(response.getPassword());
  }

  @WithMockUser(username = "user", password = "password")
  @Test
  public void createUser_fail_empty_body_field() throws Exception {
    userRequest = UserRequest.builder().userName(null).emailAddress("cisco@gmail.com")
        .preferredPhoneNumber(null).build();

    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isBadRequest());
  }

  @WithMockUser(username = "user", password = "password")
  @Test
  public void createUser_fail_empty_body() throws Exception {
    userRequest = UserRequest.builder().build();

   mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isBadRequest());
  }

  @WithMockUser(username = "user", password = "password")
  @Test
  public void getAllUsers_success() throws Exception {
    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated());

    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated());

    MvcResult mvcResult = mockMvc.perform(get("/users")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

    List<UserResponse> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

    assertEquals(2, response.size());
  }


  @WithMockUser(username = "user", password = "password")
  @Test
  public void deleteUser_success() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse response = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    mockMvc.perform(delete("/users/" + response.getUserId()))
        .andExpect(status().isNoContent());
  }

  @WithMockUser(username = "user", password = "password")
  @Test
  public void createPhone_success() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse userResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    mvcResult = mockMvc.perform(post("/users/"+userResponse.getUserId()+"/phone").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isCreated()).andReturn();

    PhoneResponse phoneResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), PhoneResponse.class);

    assertEquals(PhoneEnum.IPHONE, phoneResponse.getPhoneModel());
    assertEquals("Test", phoneResponse.getPhoneName());
    assertEquals("12344", phoneResponse.getPhoneNumber());
    assertNotNull(phoneResponse.getPhoneId());
  }


  @WithMockUser(username = "user", password = "password")
  @Test
  public void createPhone_fail_empty_body_field() throws Exception {
    phoneRequest = PhoneRequest.builder().phoneModel(PhoneEnum.IPHONE)
        .phoneName(null).phoneNumber("12344").build();

    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse userResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    mockMvc.perform(post("/users/"+userResponse.getUserId()+"/phone").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isBadRequest());
  }


  @WithMockUser(username = "user", password = "password")
  @Test
  public void createPhone_fail_empty_body() throws Exception {
    phoneRequest = PhoneRequest.builder().build();

    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse userResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    mockMvc.perform(post("/users/"+userResponse.getUserId()+"/phone").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isBadRequest());
  }

  @WithMockUser(username = "user", password = "password")
  @Test
  public void getPhonesOfUser_success() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse userResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    mockMvc.perform(post("/users/"+userResponse.getUserId()+"/phone").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isCreated()).andReturn();

    mvcResult = mockMvc.perform(get("/users/"+userResponse.getUserId()+"/phone")).andExpect(status().isOk()).andReturn();

    List<PhoneResponse> phoneResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);

    assertEquals(1, phoneResponse.size());
  }

  @Test
  public void createUser_fail_no_user() throws Exception {
    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isUnauthorized());
  }

  @WithAnonymousUser
  @Test
  public void createUser_fail_unknown_user() throws Exception {
    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isUnauthorized());
  }

}
