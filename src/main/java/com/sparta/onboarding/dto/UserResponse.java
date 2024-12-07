package com.sparta.onboarding.dto;

import com.sparta.onboarding.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
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

  public static UserResponse of(User user) {
    return UserResponse.builder()
        .username(user.getUsername())
        .nickname(user.getNickname())
        .authorities(List.of(Authority.builder()
            .authorityName(user.getRole().name())
            .build())
        )
        .build();
  }
}
