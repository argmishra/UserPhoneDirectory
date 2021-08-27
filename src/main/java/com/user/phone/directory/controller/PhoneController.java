package com.user.phone.directory.controller;

import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("phones")
@Slf4j
@Validated
public class PhoneController {

  @Autowired
  private PhoneService phoneService;

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deletePhone(@PathVariable UUID id){
    log.info("Delete phone by id {}", id);
    phoneService.deletePhone(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("{id}")
  public ResponseEntity<PhoneResponse> updatePhone(@RequestBody @Valid PhoneRequest request, @PathVariable UUID id){
    log.info("Update phone by id {}", id);
    return new ResponseEntity(phoneService.updatePhone(request, id), HttpStatus.OK);
  }



}