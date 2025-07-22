package com.your_car_your_way.chat_poc.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

  @GetMapping("api/login")
  public String getUser(){
    return "user"; // This is a placeholder. In a real application, you would return the authenticated user's details.
  }
}
