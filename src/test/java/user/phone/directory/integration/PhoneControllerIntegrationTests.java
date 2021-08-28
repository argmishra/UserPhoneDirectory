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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserPhoneDirectoryApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PhoneControllerIntegrationTests {

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
  public void deletePhone_success() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse userResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    mvcResult = mockMvc.perform(post("/users/"+userResponse.getUserId()+"/phone").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isCreated()).andReturn();

    PhoneResponse phoneResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), PhoneResponse.class);

    mockMvc.perform(delete("/phones/" + phoneResponse.getPhoneId()))
        .andExpect(status().isNoContent());
  }

  @WithMockUser(username = "user", password = "password")
  @Test
  public void updatePhone_success() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse userResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    mvcResult = mockMvc.perform(post("/users/"+userResponse.getUserId()+"/phone").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isCreated()).andReturn();

    PhoneResponse phoneResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), PhoneResponse.class);

    phoneRequest = PhoneRequest.builder().phoneModel(PhoneEnum.ANDROID)
        .phoneName("NewTest").phoneNumber("9999").build();

    mvcResult = mockMvc.perform(put("/phones/"+phoneResponse.getPhoneId()).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isOk()).andReturn();

    phoneResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), PhoneResponse.class);

    assertEquals("NewTest", phoneResponse.getPhoneName());
    assertEquals("9999", phoneResponse.getPhoneNumber());
  }

  @WithMockUser(username = "user", password = "password")
  @Test
  public void updatePhone_fail_empty_body_field() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse userResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    mvcResult = mockMvc.perform(post("/users/"+userResponse.getUserId()+"/phone").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isCreated()).andReturn();

    PhoneResponse phoneResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), PhoneResponse.class);

    phoneRequest = PhoneRequest.builder().phoneNumber("9999").build();

    mockMvc.perform(put("/phones/"+phoneResponse.getPhoneId()).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isBadRequest());
  }

  @WithMockUser(username = "user", password = "password")
  @Test
  public void updatePhone_fail_empty_body() throws Exception {
    MvcResult mvcResult = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRequest))).andExpect(status().isCreated()).andReturn();

    UserResponse userResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), UserResponse.class);

    mvcResult = mockMvc.perform(post("/users/"+userResponse.getUserId()+"/phone").contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isCreated()).andReturn();

    PhoneResponse phoneResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), PhoneResponse.class);

    phoneRequest = PhoneRequest.builder().build();

    mockMvc.perform(put("/phones/"+phoneResponse.getPhoneId()).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(phoneRequest))).andExpect(status().isBadRequest());
  }

}
