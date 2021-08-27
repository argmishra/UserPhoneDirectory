package com.user.phone.directory.dto.request;

import com.user.phone.directory.enums.PhoneEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class PhoneRequest {

  @NotBlank
  private String phoneName;

  @NotBlank
  private String phoneNumber;

  private PhoneEnum phoneModel;

}
