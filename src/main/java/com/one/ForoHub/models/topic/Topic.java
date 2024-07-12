package com.one.ForoHub.models.topic;

import com.one.ForoHub.models.response.Response;
import com.one.ForoHub.models.response.ResponseDto;
import com.one.ForoHub.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(unique = true)
   private String title;
   @Column(unique = true)
   private String message;
   private LocalDateTime date;
   private Boolean status;
   @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "author_id", referencedColumnName = "id")
   private User author;
   @Enumerated(EnumType.STRING)
   private Course course;
   @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   private List<Response> responses = new ArrayList<>();

   public Topic(TopicDto data) {
      this.title = data.title();
      this.message = data.message();
      this.date = data.date();
      this.status = true;
      this.author = new User(data.author());
      this.course = data.course();
      this.responses = data.responses().stream()
            .map(r -> new Response(r, this))
            .collect(Collectors.toList());
   }

   public void updateData(TopicUpdateDto data) {
      if (data.title() != null) {
         this.title = data.title();
      }
      if (data.message() != null) {
         this.message = data.message();
      }
      if (data.author() != null) {
         this.author = author.updateAuthor(data.author());
      }
      if (data.course() != null) {
         this.course = data.course();
      }
   }

}
