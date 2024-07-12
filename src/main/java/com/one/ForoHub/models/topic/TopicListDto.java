package com.one.ForoHub.models.topic;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record TopicListDto(
      Long id,
      String title,
      String message,
      @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime date,
      Boolean status,
      String authorName,
      Course course
) {
   public TopicListDto(Topic topic) {
      this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getDate(),
            topic.getStatus(), topic.getAuthor().getName(), topic.getCourse());
   }
}
