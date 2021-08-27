package user.phone.directory.controller;


import com.user.phone.directory.UserPhoneDirectoryApplication;
import com.user.phone.directory.controller.UserController;
import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.request.UserRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.dto.response.UserResponse;
import com.user.phone.directory.enums.PhoneEnum;
import com.user.phone.directory.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserPhoneDirectoryApplication.class })
@Transactional
public class UserControllerTests {

  @Autowired
  private UserController userController;

  @MockBean
  private UserService userService;

  private UserRequest userRequest;

  private UserResponse userResponse;

  private PhoneRequest phoneRequest;

  private PhoneResponse phoneResponse;

  private UUID userId = UUID.randomUUID();

  private UUID phoneId = UUID.randomUUID();

  @BeforeEach
  public void setup() {
    userRequest = UserRequest.builder().userName("Cisco").emailAddress("cisco@gmail.com")
        .preferredPhoneNumber("123").build();

    phoneRequest = PhoneRequest.builder().phoneModel(PhoneEnum.IPHONE)
        .phoneName("Test").phoneNumber("12344").build();

    phoneResponse = PhoneResponse.builder().phoneModel(PhoneEnum.IPHONE).phoneNumber("12344")
        .phoneName("Test").phoneId(phoneId).build();

    userResponse = UserResponse.builder().userName("Cisco").emailAddress("cisco@gmail.com")
        .preferredPhoneNumber("123").password("sssdj").userId(userId).build();
  }

  @Test
  public void createUser() {
    Mockito.when(userService.createUser(userRequest)).thenReturn(userResponse);

    ResponseEntity<UserResponse> response = userController.createUser(userRequest);

    assertEquals(201, response.getStatusCodeValue());
    assertEquals("Cisco", response.getBody().getUserName());
  }

  @Test
  public void deleteUser() {
    ResponseEntity<Void> response = userController.deleteUser(userId);

    assertEquals(204, response.getStatusCodeValue());
    verify(userService, times(1)).deleteUser(userId);
  }

  @Test
  public void getAllUsers() {
    Mockito.when(userService.getAllUsers()).thenReturn(List.of(userResponse));

    ResponseEntity<List<UserResponse>> response = userController.getAllUsers();

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(1, response.getBody().size());
  }

  @Test
  public void getPhonesOfUser() {
    Mockito.when(userService.getPhonesOfUser(userId)).thenReturn(List.of(phoneResponse));

    ResponseEntity<List<PhoneResponse>> response = userController.getPhonesOfUser(userId);


    assertEquals(200, response.getStatusCodeValue());
    assertEquals(1, response.getBody().size());
    assertEquals(PhoneEnum.IPHONE, response.getBody().get(0).getPhoneModel());
  }

  @Test
  public void createPhone() {
    Mockito.when(userService.createPhone(phoneRequest, userId)).thenReturn(phoneResponse);

    ResponseEntity<PhoneResponse> response = userController.createPhone(phoneRequest, userId);

    assertEquals(201, response.getStatusCodeValue());
    assertEquals(PhoneEnum.IPHONE, response.getBody().getPhoneModel());
  }


}
