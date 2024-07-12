package com.one.ForoHub.controllers;

import com.one.ForoHub.models.topic.*;
import com.one.ForoHub.models.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {
   @Autowired
   private TopicRepository topicRepository;
   @Autowired
   private TopicService topicService;
   @Autowired
   private UserService userService;


   @PostMapping
   @Transactional
   @Operation(summary = "Post a topic", description = "The posted topic will be stored into the database")
   public ResponseEntity<TopicListDto> postTopic(@RequestBody @Valid TopicDto data,
                                                 UriComponentsBuilder uri) {

      Topic topic = topicRepository.save(new Topic(data));

      //para encriptar clave
      userService.encryptPassword(topic);

      TopicListDto topicListDto = new TopicListDto(topic.getId(), topic.getTitle(),
            topic.getMessage(), topic.getDate(), topic.getStatus(), topic.getAuthor().getName(),
            topic.getCourse());
      URI url = uri.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();
      return ResponseEntity.created(url).body(topicListDto);
   }

   @GetMapping
   @Operation(summary = "Get the list of topics",
         description = "The list of topics will be retrieved from the database, ordered in pages" +
               " of 2 topics each and sorted by its creation date")
   public ResponseEntity<Page<TopicListDto>> listTopics(
         @PageableDefault(size = 2, sort = "date") Pageable pagination) {
      return ResponseEntity.ok(topicRepository.findAll(pagination)
            .map(TopicListDto::new));
   }

   @GetMapping("/{id}")
   @Operation(summary = "Get a specific topic", description = "Retreive a topic by its ID")
   public ResponseEntity<TopicListDto> pickTopicById(@PathVariable Long id) {
      return ResponseEntity.ok(topicService.getTopicById(id));
   }

   @PutMapping("/{id}")
   @Transactional
   @Operation(summary = "Update a topic", description = "Update the topic by its ID")
   public ResponseEntity<TopicListDto> putTopic(@RequestBody @Valid TopicUpdateDto data,
                                                @PathVariable Long id) {
      return ResponseEntity.ok(topicService.updateTopic(data, id));
   }

   @DeleteMapping("/{id}")
   @Transactional
   @Operation(summary = "Delete a topic", description = "Delete the topic indicated by its ID")
   public ResponseEntity eraseTopic(@PathVariable Long id) {
      topicService.deleteTopic(id);
      return ResponseEntity.noContent().build();
   }

}
