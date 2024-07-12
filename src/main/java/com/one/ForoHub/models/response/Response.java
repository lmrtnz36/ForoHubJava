package com.one.ForoHub.models.response;

import com.one.ForoHub.models.topic.Topic;
import com.one.ForoHub.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Response {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String message;
   private LocalDateTime date;
   private Boolean solution;
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "topic_id", referencedColumnName = "id")
   private Topic topic;
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "author_id", referencedColumnName = "id")
   private User author;


   public Response(ResponseDto data, Topic topic) {
      this.message = data.message();
      this.date = data.date();
      this.solution = data.solution();
      this.topic = topic;
      this.author = new User(data.author());
   }
}
