package com.sparta.onboarding.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SignUp {

    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 4자 이상, 10자 이하의 알파벳 소문자 및 숫자만 허용됩니다.")
    @NotBlank(message = "아이디는 비어 있을 수 없습니다.")
    private String username;

    @Pattern(
        regexp = "^[a-z0-9]{8,15}$",
        message = "비밀번호는 8자 이상, 15자 이하의 알파벳 소문자, 숫자만 허용됩니다.")
    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    private String password;

    @NotBlank(message = "닉네임은 비어 있을 수 없습니다.")
    private String nickname;

  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Sign {
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 4자 이상, 10자 이하의 알파벳 소문자 및 숫자만 허용됩니다.")
    @NotBlank(message = "아이디는 비어 있을 수 없습니다.")
    private String username;

    @Pattern(
        regexp = "^[a-z0-9]{8,15}$",
        message = "비밀번호는 8자 이상, 15자 이하의 알파벳 소문자, 숫자만 허용됩니다.")
    @NotBlank(message = "비밀번호는 비어 있을 수 없습니다.")
    private String password;
  }
}
