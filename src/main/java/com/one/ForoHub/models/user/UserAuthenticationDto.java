package com.one.ForoHub.models.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserAuthenticationDto(
      @NotBlank
      String name,
      @NotBlank
      @Pattern(regexp = "^[a-zA-Z0-9]{5,}$", message = "Need to be alphanumeric with a min of 5 char")
      String password
      ) {
}
