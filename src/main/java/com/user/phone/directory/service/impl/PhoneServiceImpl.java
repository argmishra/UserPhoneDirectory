package com.user.phone.directory.service.impl;

import com.user.phone.directory.convertor.PhoneConverter;
import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.exception.PhoneNotFoundException;
import com.user.phone.directory.model.Phone;
import com.user.phone.directory.repo.PhoneRepository;
import com.user.phone.directory.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class PhoneServiceImpl implements PhoneService {

  @Autowired
  private PhoneRepository phoneRepository;

  @Override
  public void deletePhone(UUID id){
    phoneRepository.deleteById(id);
  }

  @Override
  public PhoneResponse updatePhone(PhoneRequest request, UUID id) {
    Phone phone = this.getPhone(id);

    phone.setPhoneName(updateField(request.getPhoneName(),phone.getPhoneName()));
    phone.setPhoneNumber(updateField(request.getPhoneNumber(),phone.getPhoneNumber()));

    phone = phoneRepository.save(phone);
    return PhoneConverter.toPhoneResponse(phone);
  }

  private Phone getPhone(UUID id){
    return phoneRepository.findById(id).orElseThrow(() -> {
      throw new PhoneNotFoundException("Phone not found.");
    });
  }

  private String updateField(String newValue, String oldValue){
    if(newValue == null){
      return oldValue;
    }
    return newValue;
  }

}
