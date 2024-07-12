package com.one.ForoHub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.one.ForoHub.models.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

   @Value("${api.security.secret}")
   private String apiSecret;

   public String generateToken(User user){
      try {
         Algorithm algorithm = Algorithm.HMAC256(apiSecret);
         return JWT.create()
               .withIssuer("forohub")
               .withSubject(user.getName()) //this is the username, name in this case
               .withClaim("id", user.getId())
               .withExpiresAt(expiryDate())
               .sign(algorithm); //this is the token

      } catch (JWTCreationException exception) {
         throw new RuntimeException(); //just not to leave it empty
      }
   }


   public Instant expiryDate() {
      return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
   }


   public String getSubject(String token) {
      if (token == null) {
         throw new RuntimeException("Token invalid");
      }
      DecodedJWT verifier = null;
      try {
         Algorithm algorithm = Algorithm.HMAC256(apiSecret);
         verifier = JWT.require(algorithm)
               .withIssuer("forohub")
               .build()
               .verify(token);
         verifier.getSubject();

      } catch (JWTVerificationException exception){
         System.out.println(exception.toString());
      }
      if (verifier.getSubject() == null) {
         throw new RuntimeException("Invalid verifier");
      }
      return verifier.getSubject();
   }

}
