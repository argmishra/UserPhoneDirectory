package user.phone.directory.service;

import com.user.phone.directory.UserPhoneDirectoryApplication;
import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.request.UserRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.dto.response.UserResponse;
import com.user.phone.directory.enums.PhoneEnum;
import com.user.phone.directory.exception.UserNotFoundException;
import com.user.phone.directory.model.Phone;
import com.user.phone.directory.model.User;
import com.user.phone.directory.repo.PhoneRepository;
import com.user.phone.directory.repo.UserRepository;
import com.user.phone.directory.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserPhoneDirectoryApplication.class })
@Transactional
public class UserServiceImplTests {

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @MockBean
  private PhoneRepository phoneRepository;

  private UserRequest userRequest;

  private User user;

  private PhoneRequest phoneRequest;

  private Phone phone;

  private UUID userId = UUID.randomUUID();

  private UUID phoneId = UUID.randomUUID();

  @BeforeEach
  public void setup() {
    userRequest = UserRequest.builder().userName("Cisco").emailAddress("cisco@gmail.com")
        .preferredPhoneNumber("123").build();

    phoneRequest = PhoneRequest.builder().phoneModel(PhoneEnum.IPHONE)
        .phoneName("Test").phoneNumber("12344").build();

    phone = Phone.builder().phoneModel(PhoneEnum.IPHONE).phoneNumber("12344")
        .phoneName("Test").id(phoneId).build();

    user = User.builder().userName("Cisco").emailAddress("cisco@gmail.com")
        .preferredPhoneNumber("123").password("sssdj").id(userId).phones(List.of(phone)).build();
  }

  @Test
  public void createUser() {
    Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

    UserResponse response = userService.createUser(userRequest);

    assertEquals("Cisco", response.getUserName());
  }

  @Test
  public void deleteUser() {
    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    userService.deleteUser(userId);

    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, times(1)).delete(user);
  }

  @Test
  public void getAllUsers() {
    Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

    List<UserResponse> response = userService.getAllUsers();

    assertEquals(1, response.size());
  }

  @Test
  public void getPhonesOfUser() {
    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    List<PhoneResponse> response = userService.getPhonesOfUser(userId);

    assertEquals(1, response.size());
    assertEquals(PhoneEnum.IPHONE, response.get(0).getPhoneModel());
  }

  @Test
  public void createPhone_success() {
    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    Mockito.when(phoneRepository.save(any(Phone.class))).thenReturn(phone);

    PhoneResponse response = userService.createPhone(phoneRequest, userId);

    assertEquals(PhoneEnum.IPHONE, response.getPhoneModel());
  }

  @Test
  public void createPhone_fail() {
    Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> {
      userService.createPhone(phoneRequest, userId);
    });
  }

}
