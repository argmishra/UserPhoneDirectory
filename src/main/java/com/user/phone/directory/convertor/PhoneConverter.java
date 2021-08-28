package com.user.phone.directory.convertor;

import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.model.Phone;
import com.user.phone.directory.model.User;

import java.util.ArrayList;
import java.util.List;

public class PhoneConverter {

  public static Phone toPhone(PhoneRequest request, User user) {
    return Phone.builder().phoneModel(request.getPhoneModel()).phoneName(request.getPhoneName())
        .phoneNumber(request.getPhoneNumber()).user(user).build();
  }

  public static PhoneResponse toPhoneResponse(Phone phone) {
    return PhoneResponse.builder().phoneId(phone.getId()).phoneModel(phone.getPhoneModel())
        .phoneName(phone.getPhoneName()).phoneNumber(phone.getPhoneNumber()).build();
  }

  public static List<PhoneResponse> toPhoneResponseList(User user) {
    List<PhoneResponse> resultList = new ArrayList<PhoneResponse>();
    for(Phone p: user.getPhones()){
      PhoneResponse response = PhoneResponse.builder().phoneId(p.getId()).phoneModel(p.getPhoneModel())
          .phoneName(p.getPhoneName()).phoneNumber(p.getPhoneNumber()).build();
      resultList.add(response);
    }
   return  resultList;
  }
}
