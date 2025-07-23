package com.your_car_your_way.chat_poc.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @GetMapping("/api/user/me")
  public ResponseEntity<Map<String, String>> getCurrentUsername(Principal principal) {
      if (principal == null) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      Map<String, String> response = new HashMap<>();
      response.put("username", principal.getName());
      return ResponseEntity.ok(response);
  }
}
