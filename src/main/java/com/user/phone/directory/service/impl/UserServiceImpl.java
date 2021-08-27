package com.user.phone.directory.service.impl;

import com.user.phone.directory.convertor.PhoneConverter;
import com.user.phone.directory.convertor.UserConverter;
import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.request.UserRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.dto.response.UserResponse;
import com.user.phone.directory.exception.UserNotFoundException;
import com.user.phone.directory.model.Phone;
import com.user.phone.directory.model.User;
import com.user.phone.directory.repo.PhoneRepository;
import com.user.phone.directory.repo.UserRepository;
import com.user.phone.directory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PhoneRepository phoneRepository;

  @Override
  public UserResponse createUser(UserRequest request){
    User user = UserConverter.toUser(request);
    user = userRepository.save(user);
    return UserConverter.toUserResponse(user);
  }

  @Override
  public void deleteUser(UUID id){
    User user = this.getUserById(id);
    userRepository.delete(user);
  }

  @Override
  public List<UserResponse> getAllUsers(){
    List<User> userList = userRepository.findAll();
    return UserConverter.toUserResponseList(userList);
  }

  @Override
  public PhoneResponse createPhone(PhoneRequest request, UUID id){
    User user = this.getUserById(id);

    Phone phone = PhoneConverter.toPhone(request, user);
    phone = phoneRepository.save(phone);
    return PhoneConverter.toPhoneResponse(phone);
  }

  @Override
  public List<PhoneResponse> getPhonesOfUser(UUID id){
    User user = this.getUserById(id);
    return PhoneConverter.toPhoneResponseList(user);
  }

  private User getUserById(UUID id){
    return userRepository.findById(id).orElseThrow(() -> {
      throw new UserNotFoundException("User not found.");
    });
  }



}
