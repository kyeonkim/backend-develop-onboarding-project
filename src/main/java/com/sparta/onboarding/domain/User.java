package com.sparta.onboarding.domain;

import com.sparta.onboarding.dto.UserRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "p_user")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;

   @CreatedDate
   @Column(nullable = false, updatable = false)
   private LocalDateTime createdAt;

   @LastModifiedDate
   @Column(nullable = false)
   private LocalDateTime modifiedAt;

  public static User create(UserRequest.SignUp request, String encodedPassword) {
    return User.builder()
        .username(request.getUsername())
        .password(encodedPassword)
        .nickname(request.getNickname())
        .role(UserRole.ROLE_USER)
        .build();
  }
}
