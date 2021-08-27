package user.phone.directory.service;


import com.user.phone.directory.UserPhoneDirectoryApplication;
import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.enums.PhoneEnum;
import com.user.phone.directory.exception.PhoneNotFoundException;
import com.user.phone.directory.model.Phone;
import com.user.phone.directory.repo.PhoneRepository;
import com.user.phone.directory.service.PhoneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

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
public class PhoneServiceImplTests {

  @Autowired
  private PhoneService phoneService;

  @MockBean
  private PhoneRepository phoneRepository;

  private PhoneRequest phoneRequest;

  private Phone phone;

  private UUID phoneId = UUID.randomUUID();

  @BeforeEach
  public void setup() {
    phoneRequest = PhoneRequest.builder().phoneModel(PhoneEnum.IPHONE)
        .phoneName("Test").phoneNumber("12344").build();

    phone = Phone.builder().phoneModel(PhoneEnum.IPHONE).phoneNumber("12344")
        .phoneName("Test").id(phoneId).build();
  }

  @Test
  public void deletePhone() {
    phoneService.deletePhone(phoneId);

    verify(phoneRepository, times(1)).deleteById(phoneId);
  }

  @Test
  public void updatePhone_success() {
    Mockito.when(phoneRepository.findById(phoneId)).thenReturn(Optional.of(phone));
    Mockito.when(phoneRepository.save(any(Phone.class))).thenReturn(phone);

    PhoneResponse response = phoneService.updatePhone(phoneRequest, phoneId);

    assertEquals("Test", response.getPhoneName());
  }

  @Test
  public void updatePhone_fail() {
    Mockito.when(phoneRepository.findById(phoneId)).thenReturn(Optional.empty());

    assertThrows(PhoneNotFoundException.class, () -> {
      phoneService.updatePhone(phoneRequest, phoneId);
    });
  }
}
