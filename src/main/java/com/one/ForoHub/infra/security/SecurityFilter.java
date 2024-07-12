package com.one.ForoHub.infra.security;

import com.one.ForoHub.models.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
   @Autowired
   private TokenService tokenService;
   @Autowired
   private UserRepository userRepository;

   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws ServletException, IOException {

      var authToken = request.getHeader("Authorization");

      if (authToken != null) {
         var token = authToken.replace("Bearer ", "");
         var userName = tokenService.getSubject(token);

         if (userName != null) {
            var user = userRepository.findByName(userName);
            var authentication = new UsernamePasswordAuthenticationToken(user,
                  null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
         }
      }
      filterChain.doFilter(request, response);
   }
}
