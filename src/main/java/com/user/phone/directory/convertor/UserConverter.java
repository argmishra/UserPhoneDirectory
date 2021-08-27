package com.user.phone.directory.convertor;

import com.user.phone.directory.dto.request.UserRequest;
import com.user.phone.directory.dto.response.UserResponse;
import com.user.phone.directory.model.User;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {

  public static User toUser(UserRequest request) {
    return User.builder().userName(request.getUserName()).emailAddress(request.getEmailAddress())
        .password(RandomStringUtils.randomAlphanumeric(5))
        .preferredPhoneNumber(request.getPreferredPhoneNumber()).build();
  }

  public static UserResponse toUserResponse(User user) {
    return UserResponse.builder().userId(user.getId()).emailAddress(user.getEmailAddress())
        .password(user.getPassword()).preferredPhoneNumber(user.getPreferredPhoneNumber())
        .userName(user.getUserName()).build();
  }

  public static List<UserResponse> toUserResponseList(List<User> userList) {
    List<UserResponse> resultList = new ArrayList<UserResponse>();
    for(User u: userList){
      UserResponse response = UserResponse.builder().userId(u.getId()).userName(u.getUserName())
          .preferredPhoneNumber(u.getPreferredPhoneNumber()).emailAddress(u.getEmailAddress()).build();
      resultList.add(response);
    }
    return  resultList;
  }
}
