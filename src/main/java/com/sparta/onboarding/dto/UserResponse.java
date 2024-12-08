package com.sparta.onboarding.dto;

import com.sparta.onboarding.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponse {

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SignUpResponse {
    private String username;
    private String nickname;
    private List<Authority> authorities;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Authority {
      private String authorityName;
    }

    public static SignUpResponse of(User user) {
      return SignUpResponse.builder()
          .username(user.getUsername())
          .nickname(user.getNickname())
          .authorities(List.of(Authority.builder()
              .authorityName(user.getRole().name())
              .build())
          )
          .build();
    }
  }

  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FirstSignResponse {
    private String accessToken;
    private String refreshToken;

    public static FirstSignResponse of(String accessToken, String refreshToken) {
      return FirstSignResponse.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .build();
    }
  }

}
