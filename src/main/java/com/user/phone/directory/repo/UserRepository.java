package com.user.phone.directory.repo;

import com.user.phone.directory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(UUID id);

}
