package com.user.phone.directory.dto.request;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class UserRequest {

  @NotBlank
  private String userName;

  @NotBlank
  private String emailAddress;

  @NotBlank
  private String preferredPhoneNumber;

}