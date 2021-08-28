package com.user.phone.directory.dto.request;

import com.user.phone.directory.enums.PhoneEnum;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PhoneRequest {

  @NotBlank
  private String phoneName;

  @NotBlank
  private String phoneNumber;

  @NotNull
  private PhoneEnum phoneModel;

}
