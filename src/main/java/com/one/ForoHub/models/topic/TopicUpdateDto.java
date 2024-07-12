package com.one.ForoHub.models.topic;


import com.one.ForoHub.models.user.UserDto;
import jakarta.validation.constraints.NotNull;

public record TopicUpdateDto(
      @NotNull Long id,
      String title,
      String message,
      UserDto author,
      Course course
) {
}
