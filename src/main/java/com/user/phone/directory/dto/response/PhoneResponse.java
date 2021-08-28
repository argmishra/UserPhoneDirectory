package com.user.phone.directory.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.user.phone.directory.enums.PhoneEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneResponse {

  private UUID phoneId;

  private String phoneName;

  private PhoneEnum phoneModel;

  private String phoneNumber;

}
