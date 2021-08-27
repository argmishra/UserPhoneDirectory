package user.phone.directory.repo;

import com.user.phone.directory.UserPhoneDirectoryApplication;
import com.user.phone.directory.enums.PhoneEnum;
import com.user.phone.directory.model.Phone;
import com.user.phone.directory.model.User;
import com.user.phone.directory.repo.PhoneRepository;
import com.user.phone.directory.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserPhoneDirectoryApplication.class })
@Transactional
public class PhoneRepositoryTests {

  @Autowired
  private PhoneRepository phoneRepository;

  @Autowired
  private UserRepository userRepository;

  private Phone phone;

  private User user;

  @BeforeEach
  public void setup() {
    user = User.builder().userName("Test").emailAddress("test@gmail.com")
        .preferredPhoneNumber("123").build();
    userRepository.save(user);

    phone = Phone.builder().phoneModel(PhoneEnum.IPHONE).phoneNumber("12344")
        .phoneName("Test").user(user).build();
  }

  @Test
  public void findById() {
    phone = phoneRepository.save(phone);

    Optional<Phone> response = phoneRepository.findById(phone.getId());
    assertEquals("Test", response.get().getPhoneName());
  }

  @Test
  public void deleteById() {
    phone = phoneRepository.save(phone);

    phoneRepository.deleteById(phone.getId());

    List<Phone> response = phoneRepository.findAll();
    assertEquals(0, response.size());
  }
}
