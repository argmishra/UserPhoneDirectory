package com.user.phone.directory.repo;

import com.user.phone.directory.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

  @Query(value = "delete FROM Phone p where p.id = :id")
  @Transactional
  @Modifying
  void deleteById(UUID id);

  Optional<Phone> findById(UUID id);

}
