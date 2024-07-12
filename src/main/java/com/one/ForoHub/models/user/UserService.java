package com.one.ForoHub.models.user;

import com.one.ForoHub.models.topic.Topic;
import com.one.ForoHub.models.topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
   private TopicRepository topicRepository;

   public void encryptPassword(Topic topic) {
      topic.getAuthor().setPassword(passwordEncoder.encode(topic.getAuthor().getPassword()));
      topic.getResponses()
            .forEach(r -> r.getAuthor().setPassword(passwordEncoder.encode(r.getAuthor().getPassword())));
      userRepository.save(topic.getAuthor());
      topicRepository.save(topic);
   }
}
