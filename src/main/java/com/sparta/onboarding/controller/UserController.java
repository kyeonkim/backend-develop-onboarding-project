package com.sparta.onboarding.controller;

import com.sparta.onboarding.dto.UserRequest;
import com.sparta.onboarding.dto.UserResponse;
import com.sparta.onboarding.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  @Operation(summary = "회원가입", description = "회원가입 시 사용하는 API")
  public ResponseEntity<UserResponse.SignUpResponse> createUser(@RequestBody UserRequest.SignUp request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(request));
  }

  @PostMapping("/sign")
  @Operation(summary = "로그인", description = "로그인 시 사용하는 API")
  public ResponseEntity<UserResponse.FirstSignResponse> signIn(@RequestBody UserRequest.Sign request) {
    return ResponseEntity.ok(userService.signIn(request));
  }
}
