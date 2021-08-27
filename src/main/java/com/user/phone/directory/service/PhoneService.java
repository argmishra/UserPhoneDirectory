package com.user.phone.directory.service;

import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.response.PhoneResponse;

import java.util.UUID;

public interface PhoneService {

  void deletePhone(UUID id);

  PhoneResponse updatePhone(PhoneRequest request, UUID id);

}
