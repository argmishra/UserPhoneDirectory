package user.phone.directory.controller;

import com.user.phone.directory.UserPhoneDirectoryApplication;
import com.user.phone.directory.controller.PhoneController;
import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.enums.PhoneEnum;
import com.user.phone.directory.service.PhoneService;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserPhoneDirectoryApplication.class })
@Transactional
public class PhoneControllerTests {

  @Autowired
  private PhoneController phoneController;

  @MockBean
  private PhoneService phoneService;

  private PhoneRequest phoneRequest;

  private PhoneResponse phoneResponse;

  private final UUID phoneId = UUID.randomUUID();

  @BeforeEach
  public void setup() {
    phoneRequest = PhoneRequest.builder().phoneModel(PhoneEnum.IPHONE)
        .phoneName("Test").phoneNumber("12344").build();

    phoneResponse = PhoneResponse.builder().phoneModel(PhoneEnum.IPHONE).phoneNumber("12344")
        .phoneName("Test").phoneId(phoneId).build();
  }

  @Test
  public void deletePhone() {
    ResponseEntity<Void> response = phoneController.deletePhone(phoneId);

    assertEquals(204, response.getStatusCodeValue());
    verify(phoneService, times(1)).deletePhone(phoneId);
  }

  @Test
  public void createPhone() {
    Mockito.when(phoneService.updatePhone(any(PhoneRequest.class), any(UUID.class))).thenReturn(phoneResponse);

    ResponseEntity<PhoneResponse> response = phoneController.updatePhone(phoneRequest, phoneId);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(PhoneEnum.IPHONE, response.getBody().getPhoneModel());
  }
}
