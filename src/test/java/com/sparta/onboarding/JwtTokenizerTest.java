package com.sparta.onboarding;

import java.util.*;

import com.sparta.onboarding.jwt.JwtTokenizer;
import io.jsonwebtoken.io.Decoders;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

// TestInstane는 테스트 인스턴스의 라이프 사이클을 설정할 때 사용
// PER_METHOD: test 함수 당 인스턴스가 생성
// PER_CLASS: test 클래스 당 인스턴스가 생성
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtTokenizerTest {
  private static JwtTokenizer jwtTokenizer;
  private String secretKey;
  private String base64EncodedSecretKey;

  // 테스트에 사용할 Secret Key를 Base64 형식으로 인코딩한 후, 인코딩된 Secret Key를 각 테스트 케이스에서 사용
  @BeforeAll
  public void init() {
    jwtTokenizer = new JwtTokenizer();
    secretKey = "kevin1234123412341234123412341234";  // encoded "a2V2aW4xMjM0MTIzNDEyMzQxMjM0MTIzNDEyMzQxMjM0"

    base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(secretKey);
  }

  // Plain Text인 Secret Key가 Base64 형식으로 인코딩이 정상적으로 수행되는지 테스트
  @Test
  public void encodeBase64SecretKeyTest() {
    System.out.println(base64EncodedSecretKey);

    assertThat(secretKey, is(new String(Decoders.BASE64.decode(base64EncodedSecretKey))));
  }

  // JwtTokenizer가 Access Token을 정상적으로 생성하는지 테스트
  @Test
  public void generateAccessTokenTest() {
    Map<String, Object> claims = new HashMap<>();
    claims.put("memberId", 1);
    claims.put("roles", List.of("USER"));

    String subject = "test access token";
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, 10);
    Date expiration = calendar.getTime();

    String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

    System.out.println(accessToken);

    assertThat(accessToken, notNullValue());
  }

  // Jwt Tokenizer가 Refresh Token을 정상적으로 생성하는지 테스트
  @Test
  public void generateRefreshTokenTest() {
    String subject = "test refresh token";
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR, 24);
    Date expiration = calendar.getTime();

    String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

    System.out.println(refreshToken);

    assertThat(refreshToken, notNullValue());
  }

}
