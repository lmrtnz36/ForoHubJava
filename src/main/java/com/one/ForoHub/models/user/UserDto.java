package com.one.ForoHub.models.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserDto(
      @NotBlank
      String name,
      @NotBlank @Email
      String email,
      @NotBlank
      @Pattern(regexp = "^[a-zA-Z0-9]{5,}$", message = "Need to be alphanumeric with a min of 5 char")
      String password
) {
}
