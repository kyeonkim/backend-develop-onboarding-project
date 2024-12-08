package com.sparta.onboarding.service;

import com.sparta.onboarding.domain.User;
import com.sparta.onboarding.dto.UserRequest;
import com.sparta.onboarding.dto.UserRequest.SignUp;
import com.sparta.onboarding.dto.UserResponse;
import com.sparta.onboarding.jwt.JwtTokenizer;
import com.sparta.onboarding.repository.UserRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenizer jwtTokenizer;

  @Value("${jwt.secret-key}")
  private String secretKey;

  @Value("${jwt.access-token-expiration}")
  private long accessTokenExpirationMs;

  @Value("${jwt.refresh-token-expiration}")
  private long refreshTokenExpirationMs;

  // 회원가입 로직
  public UserResponse.SignUpResponse signUp(SignUp request) {
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    User user = userRepository.save(User.create(request, encodedPassword));
    return UserResponse.SignUpResponse.of(user);
  }

  // 로그인 로직
  public UserResponse.FirstSignResponse signIn(UserRequest.Sign request) {
    User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    String encodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);

    Map<String, Object> claims = new HashMap<>();
    claims.put("username", user.getUsername());
    claims.put("role", user.getRole());

    String accessToken = jwtTokenizer.generateAccessToken(
        claims,
        user.getUsername(),
        new Date(System.currentTimeMillis() + accessTokenExpirationMs),
        encodedSecretKey
    );

    String refreshToken = jwtTokenizer.generateRefreshToken(
        user.getUsername(),
        new Date(System.currentTimeMillis() + refreshTokenExpirationMs),
        encodedSecretKey
    );

    return UserResponse.FirstSignResponse.of(accessToken, refreshToken);
  }

}
