package com.user.phone.directory.service;

import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.request.UserRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

  UserResponse createUser(UserRequest request);

  void deleteUser(UUID id);

  List<UserResponse> getAllUsers();

  PhoneResponse createPhone(PhoneRequest request, UUID id);

  List<PhoneResponse> getPhonesOfUser(UUID id);

}
