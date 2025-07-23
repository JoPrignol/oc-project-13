package com.your_car_your_way.chat_poc.controllers;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class UserController {
  @GetMapping("/api/user/me")
  public ResponseEntity<String> getCurrentUsername(Principal principal) {
      if (principal == null) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
      return ResponseEntity.ok(principal.getName());
  }
}
