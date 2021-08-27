package com.user.phone.directory.controller;

import com.user.phone.directory.dto.request.PhoneRequest;
import com.user.phone.directory.dto.request.UserRequest;
import com.user.phone.directory.dto.response.PhoneResponse;
import com.user.phone.directory.dto.response.UserResponse;
import com.user.phone.directory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
@Slf4j
@Validated
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request){
    log.info("Creating User");
    return new ResponseEntity(userService.createUser(request), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsers(){
    log.info("Get all users");
    return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
    log.info("Delete user by id {}", id);
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("{id}/phone")
  public ResponseEntity<PhoneResponse> createPhone(@RequestBody @Valid PhoneRequest request, @PathVariable UUID id){
    log.info("Creating phone for user id {}", id);
    return new ResponseEntity(userService.createPhone(request, id), HttpStatus.CREATED);
  }

  @GetMapping("{id}/phone")
  public ResponseEntity<List<PhoneResponse>> getPhonesOfUser(@PathVariable UUID id){
    log.info("Get all phones by user id {}", id);
    return new ResponseEntity(userService.getPhonesOfUser(id), HttpStatus.OK);
  }




}