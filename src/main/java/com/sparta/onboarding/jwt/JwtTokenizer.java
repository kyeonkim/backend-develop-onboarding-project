package com.sparta.onboarding.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenizer {

  // 사용자에게 JWT를 최초로 발급해주기 위한 JWT 생성
  public String generateAccessToken(Map<String, Object> claims,
      String subject,
      Date expiration,
      String base64EncodedSecretKey) {
    Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(Calendar.getInstance().getTime())
        .expiration(expiration)
        .signWith(key)
        .compact();
  }

  // Access Token이 만료되었을 경우, 새로 생성할 수 있게 해주는 Refresh Token을 생성
  public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
    Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

    return Jwts.builder()
        .subject(subject)
        .issuedAt(Calendar.getInstance().getTime())
        .expiration(expiration)
        .signWith(key)
        .compact();
  }

  // JWT Token이 유효한지 검증
  public void verifySignature(String jws, String base64EncodedSecretKey) {
    SecretKey key = (SecretKey) getKeyFromBase64EncodedKey(base64EncodedSecretKey);

    try {
      Jws<Claims> claimsJws = Jwts.parser()
          .verifyWith(key)
          .build()
          .parseSignedClaims(jws);

      Claims claims = claimsJws.getBody();
      log.debug("JWT 서명이 확인되었습니다.");
    } catch (ExpiredJwtException e) {
      log.error("토큰이 만료되었습니다: {}", e.getMessage());
      throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "만료된 JWT 토큰입니다.");
    } catch (JwtException e) {
      log.error("서명 검증 실패: {}", e.getMessage());
      throw new JwtException("서명이 잘못된 JWT입니다.");
    }
  }

  // Plain Text 형태인 Secret Key의 byte[]를 Base64 형식의 문자열로 인코딩
  public String encodeBase64SecretKey(String secretKey) {
    return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  // JWT의 서명에 사용할 Secret Key를 생성
  private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);  // Base64 형식으로 인코딩된 Secret Key를 디코딩한 후, byte array를 반환
    Key key = Keys.hmacShaKeyFor(keyBytes);    // key byte array를 기반으로 적절한 HMAC 알고리즘을 적용한 Key 객체를 생성

    return key;
  }
}
