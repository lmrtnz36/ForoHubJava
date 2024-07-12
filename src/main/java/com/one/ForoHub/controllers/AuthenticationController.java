package com.one.ForoHub.controllers;

import com.one.ForoHub.infra.security.JWTokenDto;
import com.one.ForoHub.infra.security.TokenService;
import com.one.ForoHub.models.user.User;
import com.one.ForoHub.models.user.UserAuthenticationDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

   @Autowired
   private AuthenticationManager authenticationManager;
   @Autowired
   private TokenService tokenService;


   @PostMapping
   @Operation(summary = "Log in", description = "Log in using a user name and a password")
   public ResponseEntity UserAuthentication(@RequestBody @Valid UserAuthenticationDto userAuthenticationDto) {

      Authentication authToken = new UsernamePasswordAuthenticationToken(
            userAuthenticationDto.name(), userAuthenticationDto.password());

      var authenticatedUser = authenticationManager.authenticate(authToken);

      var JWToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());

      return ResponseEntity.ok(new JWTokenDto(JWToken));
   }

}
