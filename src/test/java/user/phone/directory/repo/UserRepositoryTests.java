package user.phone.directory.repo;

import com.user.phone.directory.UserPhoneDirectoryApplication;
import com.user.phone.directory.model.User;
import com.user.phone.directory.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { UserPhoneDirectoryApplication.class })
@Transactional
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;

	private User user;

	@BeforeEach
	public void setup() {
		user = User.builder().userName("Test").emailAddress("test@gmail.com")
				.preferredPhoneNumber("123").build();
		userRepository.save(user);
	}

	@Test
	public void findById() {
		user = userRepository.save(user);

		Optional<User> response = userRepository.findById(user.getId());
		assertEquals("Test", response.get().getUserName());
	}

}
