package com.sparta.onboarding.service;

import com.sparta.onboarding.domain.User;
import com.sparta.onboarding.dto.UserRequest.SignUp;
import com.sparta.onboarding.dto.UserResponse;
import com.sparta.onboarding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponse signUp(SignUp request) {
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    User user = userRepository.save(User.create(request, encodedPassword));
    return UserResponse.of(user);
  }

}
